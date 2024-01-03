# my_mutant_testing

项目结构：
```text
├── compile-mutants.sh # 批量编译变异体
├── pom.xml
├── pool  # Mutant Pool，变异生成的变异体
├── src     
│   ├── main # My变异引擎与变异执行实现
│   └── test # 一些Javaparser使用样例
└── testsuite # 独立出来的测试套件类
```

本模块的主要内容集中在main目录下，包括：

- `MutationEngine`: 基于Javaparser实现的Source-level变异引擎，对变异测试中的下列三个环节进行了简单实现：
    1. **变异算子选择**: 确定变异测试所用的变异算子。本模块提供了五种基础变异算子实现`ABSMutator`,`AORMutator`,`LCRMutator`,`RORMutator`,`UOIMutator`和两种复合变异算子`UnaryMutator`,`BinaryMutator`。`ABSMutator`能够对一元表达式`(e)`进行绝对值变异。`AORMutator`能够对二元运算符`+,-,*,/`进行变异。`LCRMutator`能够对二元运算符`$$,||`进行变异。`RORMutator`能够对二元运算符`==,!=,<,<=,>,>=`进行变异。`UOIMutator`能够对一元运算符`+,-`进行变异。`UnaryMutator`能够对所有一元表达式和运算符进行变异。`BinaryMutator`能够对所有二元运算符进行变异。
    2. **变异体构建**: 利用选定的变异算子生成变异体，包括变异点定位和实施变异两个步骤。
- `MutantExecution`: 利用给定的测试套件执行变异体，计算变异得分。
- `compile-mutants.sh`: 将生成的变异体（.java文件）批量编译成.class文件。
- `Calculator`: 一个简单的类，包含`add`，`substract`，`multiply`三个方法。
- `CalculatorTestSuite`: `Calculator`的简单测试套件，通过抛出异常的方式表现测试不通过。
