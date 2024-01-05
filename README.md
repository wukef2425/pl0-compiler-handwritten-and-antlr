# pl0-compiler-handwritten-and-antlr

本项目是对编译器设计的深入探索，分为两个主要任务：

- **任务1**: 为修改过的PL/0语法设计的编译器。这一部分项目读取PL/0源代码，将其翻译成三地址代码。

- **任务2**: 基于ANTLR的编译器。该任务利用ANTLR工具从PL/0源代码自动生成语法树。访问者方法重写了ANTLR提供的基类方法，以在遇到相应的语法规则时执行特定的动作。

我们在这个项目中使用的语法不是传统的PL/0语法。请参阅文档：[PL/0语法规则](docs/2023-12-21第二次会议/pl0语法规则.md).

Welcome to the repository of our PL/0 Compiler Collection. This project is an in-depth exploration of compiler design, split into two main tasks, each focusing on a different aspect of compiler construction:

- **Task1**: A handcrafted compiler for a modified PL/0 grammar. This part of the project reads PL/0 source code and translates it into three-address code, an intermediate representation that is well-suited for further optimization and execution by a virtual machine.

- **Task2**: This task leverages the ANTLR tool to automatically generate a parse tree from PL/0 source code. By visiting different nodes of the tree with custom visitor methods, the compiler translates the code into an intermediate three-address code representation.

The grammar we use in this project is not the traditional PL/0. 

For a comprehensive understanding of the modifications made to the PL/0 grammar and the structure of the generated three-address code, please refer to our documentation: [Modified PL/0 Grammar Rules](docs/2023-12-21第二次会议/pl0语法规则.md).

---

This repository is a great resource for those looking to understand compiler construction with a practical example. Contributions, suggestions, and discussions are always welcome.
