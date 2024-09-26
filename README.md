# TON VM Disassembler based on Java

> Provides Fift-like code from smart contract source.
> Latest Tonlib libraries can be
found [here](https://github.com/ton-blockchain/ton/actions).

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
