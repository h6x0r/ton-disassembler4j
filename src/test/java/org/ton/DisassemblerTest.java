package org.ton;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.java.address.Address;
import org.ton.java.cell.Cell;
import org.ton.java.fift.FiftRunner;
import org.ton.java.func.FuncRunner;
import org.ton.java.tonlib.Tonlib;
import org.ton.java.tonlib.types.AccountState;
import org.ton.java.tonlib.types.FullAccountState;
import org.ton.java.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisassemblerTest {

    private static final String BASE_DIR = "src/test/resources/";
    private static final String BOC_DIR = BASE_DIR + "bocs/";
    private static final String TXT_DIR = BASE_DIR + "snapshots/";

    private static Tonlib tonlib;

    @BeforeAll
    public static void init() {
        tonlib = Tonlib.builder()
                .testnet(false)
                .ignoreCache(false)
                .build();
    }

    @Test
    public void shouldDisassembleConfig() throws Exception {
        byte[] boc = fetchCodeOrSnapshot("Ef9VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVbxn");
        String result = normalizeLines(Disassembler.fromBoc(boc));
        String actual = loadSnapshot("config");

        assertEquals(actual, result);
    }

    @Test
    public void shouldDisassembleNft() throws Exception {
        byte[] boc = fetchCodeOrSnapshot("EQBmG4YwsdGsUHG46rL-_GtGxsUrdmn-8Tau1DKkzQMNsGaW");
        String result = normalizeLines(Disassembler.fromBoc(boc));
        assertEquals(loadSnapshot("nft"), result);
    }

    @Disabled
    @Test
    public void shouldDumpMethod() throws Exception {
        String code = loadFiftFromFunc();
        Cell codeCell = Cell.fromBoc(code);
        String result = Disassembler.fromCode(codeCell);
        assertEquals(loadSnapshot("dump"), result);
    }

    @Test
    public void shouldDisassembleElector() throws Exception {
        byte[] boc = fetchCodeOrSnapshot("Ef8zMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzM0vF");
        String result = normalizeLines(Disassembler.fromBoc(boc));
        assertEquals(loadSnapshot("elector"), result);
    }

    @Test
    public void shouldDisassembleContract() throws Exception {
        byte[] boc = fetchCodeOrSnapshot("EQBRrTk63wHpvreMs7_cDKWh6zrYmQcSBOjKz1i6GcbRTLZX");
        String result = normalizeLines(Disassembler.fromBoc(boc));
        assertEquals(loadSnapshot("contract"), result);
    }

    @Test
    public void shouldDisassembleNumber5() throws Exception {
        byte[] boc = fetchCodeOrSnapshot("EQDSbgHX03B9_0cNBAMdlmhVbvhRNYhZNhTRH4wfNBmisKB5");
        String result = normalizeLines(Disassembler.fromBoc(boc));
        assertEquals(loadSnapshot("numberFive"), result);
    }

    private byte[] fetchCodeOrSnapshot(String addr) throws Exception {
        Path bocPath = Paths.get(BOC_DIR, addr + ".boc");
        if (Files.exists(bocPath)) {
            return Files.readAllBytes(bocPath);
        }

        Address address = Address.of(addr);
        FullAccountState accountState = tonlib.getAccountState(address);
        byte[] code = Utils.base64ToBytes(accountState.getAccount_state().getCode());

        Files.createDirectories(Paths.get(BOC_DIR));
        Files.write(bocPath, code);

        return code;
    }


    private String loadSnapshot(String addr) throws IOException {
        Path snapshotPath = Paths.get(TXT_DIR, addr + ".txt");
        if (Files.exists(snapshotPath)) {
            return Files.readString(snapshotPath);
        }
        else {
            throw new IOException("Snapshot not found: " + snapshotPath);
        }
    }

    private String loadFiftFromFunc() throws IOException {
        String rawInstruction = """
                () main() {

                }

                () owner() method_id {

                }""";

        String funcFileName = "test.fc";
        File funcFile = Paths.get(BASE_DIR + funcFileName).toFile();

        if (Files.notExists(funcFile.toPath())) {
            Files.write(funcFile.toPath(), rawInstruction.getBytes());
        }

        FuncRunner funcRunner = FuncRunner.builder().build();
        String fiftCode = funcRunner.run(funcFile.getParent(), "-PA", funcFile.getAbsolutePath());

        String fiftFileName = "test.fift";
        File fiftFile = Paths.get(BASE_DIR + fiftFileName).toFile();

        if (Files.notExists(fiftFile.toPath())) {
            Files.write(fiftFile.toPath(), fiftCode.getBytes());
        }

        FiftRunner fiftRunner = FiftRunner.builder().build();

        return fiftRunner.run(fiftFile.getParent(), "-s", fiftFile.getAbsolutePath());
    }

    // visually exactly the same lines would be generated
    // normalizes default file formatting
    private String normalizeLines(String s) {
        return s
                .replaceAll("\r", "")
                .replaceAll("\n", "\r\n");
    }
}