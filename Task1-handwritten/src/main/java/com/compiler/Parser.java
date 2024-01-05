package com.compiler;

public class Parser {
    LexAnalysis lex;

    private SymbolTable STable = new SymbolTable();
    private String filename;
    public Parser(String filename) {
        lex = new LexAnalysis(filename);
        lex.preManage();
        this.filename=filename;
    }

    public void analysis(){
        lex.nextToken();
        procedure();

        if (EnumErrors.getNum() == 0) {
            System.out.println("三地址代码：");
            codeGen.printAllToFile(this.filename);
            System.out.println("符号表：");
            STable.printTable();
        }

    }
    int tempId=0;
    private ThreeAddressCodeGen codeGen=new ThreeAddressCodeGen(); //生成及存放三地址代码
    private int getNewTempId(){
        tempId++;
        return tempId;
    }

    public void procedure() {
        header();
        subprocedure();
    }
    public void header(){
        if(lex.getType()!=EnumChar.procsy){
            EnumErrors.error(EnumErrors.noProgram);
        }else{
            lex.nextToken();
        }

        if(lex.getType()!=EnumChar.ident){
            EnumErrors.error(EnumErrors.noProgramIdent);//没有程序标识符
        }else {
            lex.nextToken();
        }

    }
//<分程序>→[<常量说明>][<变量说明>]<语句>
    public void subprocedure(){

        if (lex.getType() == EnumChar.constsy) {
            constDeclare();
        }

        if (lex.getType() == EnumChar.varsy) {
            varDeclare();
        }

        statement();
    }

    //<常量说明>→CONST <常量定义>{，<常量定义>} ;
    private void constDeclare() {
        constDefinition();//必须有一个常量定义

        while(true){
            if(lex.getType()==EnumChar.comma){
                constDefinition();
            }else if(lex.getType()==EnumChar.semicolon){
                lex.nextToken();
                break;
            }else{
                EnumErrors.error(EnumErrors.noCommaSeperateConst);
                break;
            }
        }

    }
    //<常量定义>→<标识符>:=<⽆符号整数>
    //确保结束后lex指向下一个
    private void constDefinition(){
        String identname;
        String identvalue;
        identname = lex.nextToken();
        lex.nextToken();
        if(lex.getType()==EnumChar.becomes) {
            int p=enterTable(identname, EnumChar.constsy);
            identvalue=lex.nextToken();

            if (lex.getType() == EnumChar.intcon) {
                codeGen.emit(identname + " " + ":=" + " " + identvalue);
                STable.getRow(p).setIsAssigned();
                lex.nextToken();
            } else {
                EnumErrors.error(EnumErrors.illegalConstVal);
            }
        }
        else{
            EnumErrors.error(EnumErrors.noConstDeclaration);
        }

    }
    //<变量说明>→VAR<标识符>{，<标识符>};
    //确保结束后lex指向下一个
    private void varDeclare()                       //变量声明处理
    {
        String identname;
        identname = lex.nextToken();
        if(lex.getType()== EnumChar.ident){
            enterTable(identname, EnumChar.varsy);
            lex.nextToken();
        }else{
            EnumErrors.error(EnumErrors.noVarDeclaration);
        }
        while (true) {

            if(lex.getType()==EnumChar.comma){
                identname = lex.nextToken();
                if(lex.getType()== EnumChar.ident){
                    enterTable(identname, EnumChar.varsy);
                    lex.nextToken();
                }else{
                    EnumErrors.error(EnumErrors.noVarDeclaration);
                }
            }else if(lex.getType()==EnumChar.semicolon){
                lex.nextToken();
                break;
            }else{
                EnumErrors.error(EnumErrors.noCommaSeperateVar);
                break;//不然无限死循环
            }
        }
    }

    //<复合语句>→BEGIN <语句>{；<语句>} END
    private void compoundStatement(){
        lex.nextToken();
        statement();


        while(true) {
            if(lex.getType()==EnumChar.semicolon){
                lex.nextToken();
                statement();
            }
            else if(lex.getType()==EnumChar.endsy) {
                lex.nextToken();
                break;
            }else {

                EnumErrors.error(EnumErrors.noEnd);
                break;//不然无限死循环
            }
        }
    }
    //<语句>→<赋值语句> | <条件语句 >| <循环语句> | <复合语句> | <空语句>
    private void statement(){

        if (lex.getType() == EnumChar.ident) { //赋值语句
                assignmentStatement();
        } else if (lex.getType() == EnumChar.ifsy) {//条件语句
                ifStatement();
        }else if (lex.getType()==EnumChar.whilesy) {//循环语句
                 whileStatement();
        }else if(lex.getType()==EnumChar.beginsy){// <复合语句>
            compoundStatement();
        }//也可以是空语句
    }
    //<赋值语句>→<标识符>:=<表达式>
    private void assignmentStatement() {
        String identName=lex.getStrToken();
        int rowNum=locateTableWithChecking(identName);
        if(STable.getRow(rowNum).getType() == EnumChar.constsy) {
            EnumErrors.error(EnumErrors.constAssigenment);//常量不能被赋值
        }
        lex.nextToken();
        if(lex.getType()==EnumChar.becomes) {
            lex.nextToken();
            String assignValue=expression();
            codeGen.emit(identName+" "+":="+" "+assignValue);
            STable.getRow(rowNum).setIsAssigned();
        }else{
            EnumErrors.error(EnumErrors.noBecomes);      //缺少赋值符号
        }

    }

    //<条件语句>→IF <条件> THEN <语句>
    private void ifStatement()          //判断语句处理
    {
        String code="if";
        lex.nextToken();
        String cond = condition();
        code+=" "+cond;

        if (lex.getType() == EnumChar.thensy) {
            lex.nextToken();
        } else {
            EnumErrors.error(EnumErrors.noThen);   //缺少then
        }

        code+=" "+"goto"+" "+(codeGen.getCurrentAddrId()+3);//真出口
        codeGen.emit(code);

        int falseList=codeGen.nextAddr();//占了一条假出口,假出口也是跳转型
        statement();//真出口代码解析完了

        int lastAddr=codeGen.getCurrentAddrId();//真出口代码最后一句地址

        codeGen.setAddrCode(falseList,"goto"+" "+(lastAddr+1));//
    }
    //<循环语句>→WHILE <条件> DO <语句>
    private void whileStatement()         //循环语句处理
    {
        String code="if";
        lex.nextToken();
        String cond = condition();
        code+=" "+cond;

        if (lex.getType() == EnumChar.dosy) {
            lex.nextToken();
        } else{
            EnumErrors.error(EnumErrors.noDo);
        }

        code+=" "+"goto"+" "+(codeGen.getCurrentAddrId()+3);//真出口
        int whileAddr=codeGen.emit(code);//while语句的地址


        int falseList=codeGen.nextAddr();//占了一条假出口,假出口也是跳转型
        statement();//真出口代码解析完了
        codeGen.emit("goto"+" "+whileAddr);//返回while语句的地址
        int lastAddr=codeGen.getCurrentAddrId();//真出口代码最后一句地址
        codeGen.setAddrCode(falseList,"goto"+" "+(lastAddr+1));//假出口代码补写上了
    }

    //<表达式>→[+|-]项 | <表达式> <加法运算符> <项>
    //消除左递归
    //<表达式>→[+|-]项 <加法运算符>  <项> <加法运算符> <项>...
    //扫描完后，确保lex指向下一个
    private String expression()         // 表达式处理
    {
        String val;
        EnumChar op;
        op = lex.getType();
        if (op == EnumChar.plussy || op == EnumChar.minussy) {
            lex.nextToken();
            val = term();
            if (op == EnumChar.minussy) {
                codeGen.emit(val+" := -"+val);//取负指令
            }
        } else {//没有符号的情况
            val = term();
        }

        op = lex.getType();
        while(op == EnumChar.plussy || op == EnumChar.minussy) {
            lex.nextToken();
            String val1 = term();
            String tempVariable="T"+getNewTempId();
            if (op == EnumChar.plussy) {
                codeGen.emit(tempVariable+" := "+val+" + "+val1);
            } else {
                codeGen.emit(tempVariable+" := "+val+" - "+val1);
            }
            val=tempVariable;
            op=lex.getType();
        }

        return val;
    }

    //<条件>→<表达式> <关系运算符> <表达式>
    //扫描完后，确保lex指向下一个
    private String condition( )        //条件 处理
    {
        String  ex1,ex2,opstr;

        ex1=expression();
        opstr= relationalOperator();
        ex2=expression();

        return ex1+" "+opstr+" "+ex2;
    }
    //<关系运算符>→ = | <> | < | <= | > | >=
    private String relationalOperator()
    {
        EnumChar op;
        String opstr="";
        op=lex.getType();
        switch(op){
            case eql:
                opstr="=";
                break;
            case neq:
                opstr="≠";
                break;
            case lss:
                opstr="<";
                break;
            case leq:
                opstr="<=";
                break;
            case gtr:
                opstr=">";
                break;
            case geq:
                opstr=">=";
                break;
            default:
                EnumErrors.error(EnumErrors.illegalRelationalOperator);
        }
        lex.nextToken();
        return opstr;
    }

    //

     //	<项> => <因子>|<项><乘法运算符><因子>
     //	可以等价为：
     //	<项> => <因子> { <乘法运算符> <因子> }
    // 扫描完后，确保lex指向下一个
    private String term()                 //项处理    <项>→<因⼦> | <项><乘法运算符> <因⼦>
    {
        String val;
        EnumChar op;
        val=factor();
        op=lex.getType();
        while(op==EnumChar.multisy || op==EnumChar.divsy) {
            lex.nextToken();
            String val1=factor();
            String tempVariable="T"+getNewTempId();
            if (op == EnumChar.multisy) {
                codeGen.emit(tempVariable+" := "+val+" * " +val1);
            } else if (op ==EnumChar.divsy) {
                codeGen.emit(tempVariable+" := "+val+" / "+val1);
            }
            val=tempVariable;
            op=lex.getType();
        }
        return val;
    }

    //因子处理  <因⼦>→<标识符> |<⽆符号整数> | (<表达式>)
    private String factor()
    {
        String val="";
        EnumChar type=lex.getType();
        if(type==EnumChar.ident) {
            val=lex.getStrToken();
            int p=locateTableWithChecking(val);
            if(!STable.getRow(p).isAssigned()) {
                EnumErrors.error(EnumErrors.noAssignToUse);
            }
            lex.nextToken();
        }else if(type==EnumChar.lparent) {
            lex.nextToken();
            val=expression();
            if(lex.getType()==EnumChar.rparent) {
                lex.nextToken();
            }else {
                EnumErrors.error(EnumErrors.noRparent);
            }
        }else if(type== EnumChar.intcon){
            val=lex.getStrToken();
            lex.nextToken();
        }else {
            EnumErrors.error(EnumErrors.exInnerFault);
        }
        return val;
    }//factor end

    private int enterTable(String name, EnumChar type) {
        int i;
        if(STable.isFull()){
            EnumErrors.error(EnumErrors.symbolTableOverflow);
            return 0;//符号表溢出
        }else{
            if(STable.checkExistence(name)>0){
                EnumErrors.error(EnumErrors.identiOverDefine);
                return 0;//标识符重定义
            }else{
                 return STable.enterTable(name, type);
            }
        }
    }
//在符号表中找标识名
    private int locateTableWithChecking(String name) {
        int p= STable.checkExistence(name);
        if(p==0){
            LexAnalysis.isNewLine=0;
            EnumErrors.error(EnumErrors.undefinedIdent); ; //未定义的标识符
        }
        return p;
    }
}
