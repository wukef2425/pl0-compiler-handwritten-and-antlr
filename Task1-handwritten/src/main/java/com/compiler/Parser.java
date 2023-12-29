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
    int tempId=0;
    private ThreeAddressCodeGen codeGen=new ThreeAddressCodeGen(); //生成及存放三地址代码
    private int getNewTempId(){
        tempId++;
        return tempId;
    }
    public boolean ParserAnalysis() {
        lex.nextToken();
        procedure();
        if (EnumErrors.getNum() == 0 || true) {
            System.out.println("三地址代码：");
            codeGen.printAll();
            System.out.println("符号表：");
            STable.printTable();
        }
        return EnumErrors.getNum() != 0;
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

    public void subprocedure(){
        if (lex.getType() == EnumChar.constsy) {
            constDeclare();
            lex.nextToken();
        }

        if (lex.getType() == EnumChar.varsy) {
            varDeclare();
            lex.nextToken();
        }

        statementPart();
    }

    private void constDeclare() {
        constDefinition();//必须有一个常量定义
        lex.nextToken();
        while(true){
            if(lex.getType()==EnumChar.comma){
                constDefinition();
            }else if(lex.getType()==EnumChar.semicolon){
                break;
            }else{
                EnumErrors.error(EnumErrors.noCommaSeperate);
            }
            lex.nextToken();
        }

    }
    private void constDefinition(){
        String identname;
        String identvalue;
        identname = lex.nextToken();
        if(lex.getType()==EnumChar.ident){
            enterTable(identname,EnumChar.constsy);
            lex.nextToken();
            if(lex.getType()==EnumChar.eql){
                identvalue= lex.nextToken();
                if(lex.getType()==EnumChar.intcon){
                    codeGen.emit(identname+" "+":="+" "+identvalue);
                }else{
                    EnumErrors.error(EnumErrors.illegalConstVal);
                }
            }else{
                EnumErrors.error(EnumErrors.noEqlforConst);
            }
        }else{
            EnumErrors.error(EnumErrors.noConstDeclaration);
        }
    }
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
                break;
            }else{
                EnumErrors.error(EnumErrors.noCommaSeperate);
            }
            lex.nextToken();
        }
    }

    //<语句部分>→<语句> | <复合语句>
    private void statementPart(){
        if (lex.getType() == EnumChar.beginsy) {
            compoundStatement();
        } else {
            statement();
        }
    }
    //<复合语句>→BEGIN <语句>{；<语句>} END
    private void compoundStatement(){
        lex.nextToken();
        statement();
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
        }
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
        String tempVariable="T"+getNewTempId();
        codeGen.emit(tempVariable+" = "+ex1+" + "+ex2);
        return tempVariable;
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
        lex.nextToken();
        op=lex.getType();
        while(op==EnumChar.multisy || op==EnumChar.divsy) {
            lex.nextToken();
            String val1=factor();
            String tempVariable="T"+getNewTempId();
            if (op == EnumChar.multisy) {
                codeGen.emit(tempVariable+" := "+val+" *" +val1);
            } else if (op ==EnumChar.divsy) {
                codeGen.emit(tempVariable+" := "+val+" / "+val1);
            }
            val=tempVariable;
            op=lex.getType();
        }
        return val;
    }

    //因子处理  <因⼦>→<标识符> |<常量> | (<表达式>)
    private String factor()
    {
        String val="";
        EnumChar type=lex.getType();
        if(type==EnumChar.ident) {
            val=lex.getStrToken();
            locateTableWithChecking(val);
            lex.nextToken();
        }else if(type==EnumChar.lparent) {
            lex.nextToken();
            val=expression();
            if(lex.getType()==EnumChar.rparent) {
                lex.nextToken();
            }else {
                EnumErrors.error(EnumErrors.noRparent);
            }
        }////常量的定义是？？？？？
        return val;
    }//factor end

    private void enterTable(String name, EnumChar type) {
        int i;
        if(STable.isFull()){
            EnumErrors.error(EnumErrors.symbolTableOverflow);    //符号表溢出
        }else{
            if(STable.checkExistence(name)>0){
                EnumErrors.error(EnumErrors.identiOverDefine);    //标识符重定义
            }else{
                STable.enterTable(name, type);
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
