# TON VM Disassembler based on Java

[![License: MIT](https://img.shields.io/badge/License-MIT-white.svg)](https://opensource.org/licenses/MIT)
[![Based on TON][ton-svg]][ton]
[![ton4j](https://img.shields.io/maven-central/v/io.github.neodix42/smartcontract?label=ton4j)](https://mvnrepository.com/artifact/io.github.neodix42/smartcontract)
![GitHub last commit](https://img.shields.io/github/last-commit/h6x0r/ton-disassembler4j)


> Provides Fift-like code from smart contract source.
> Latest Tonlib libraries can be
found [here](https://github.com/ton-blockchain/ton/actions).

## Maven [![Maven Central][maven-central-svg]][maven-central]

```xml
<dependency>
    <groupId>io.github.h6x0r</groupId>
    <artifactId>ton-disassembler4j</artifactId>
    <version>2.0.2</version>
</dependency>
```

## Usage
```java
Tonlib tonlib = Tonlib.builder()
                .testnet(false)
                .ignoreCache(false)
                .build();

Address address = Address.of(addr);
FullAccountState accountState = tonlib.getAccountState(address);

byte[] accountStateCode = Utils.base64ToBytes(accountState.getAccount_state().getCode());

String disassembledInstruction = Disassembler.fromBoc(accountStateCode);
```

## Support ton-java development
If you want to speed up ton-java development and thus change its priority in my backlog, you are welcome to donate some toncoins:

```UQBcZMSqgAHLsMQ2lOx9GPrBuNYvuzfPvfqAwZot4g82-zXq```

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=h6x0r/ton-disassembler4j&type=Date)](https://star-history.com/#h6x0r/ton-disassembler4j&Date)

[maven-central-svg]: https://img.shields.io/maven-central/v/io.github.h6x0r/ton-disassembler4j?color=red

[maven-central]: https://mvnrepository.com/artifact/io.github.h6x0r/ton-disassembler4j

[ton-svg]: https://img.shields.io/badge/Based%20on-TON-blue

[ton]: https://ton.org
