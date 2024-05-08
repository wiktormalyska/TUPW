package de.db.bcm.tupw;

public class TestPackage {
    public static void main(String[] args) {
        //TODO: remove this test code before release

        // There are many ways to specify the HMAC key.
        // One way is simply to use a static HMAC key which is only known to the program.
        // TODO: Do not use this constant byte array. Roll your own!!!!
        final byte[] HMAC_KEY = {(byte) 0x53, (byte) 0x67, (byte) 0xC3, (byte) 0x59,
                (byte) 0x4B, (byte) 0x46, (byte) 0x0F, (byte) 0xFA,
                (byte) 0x15, (byte) 0x21, (byte) 0x13, (byte) 0x6C,
                (byte) 0x7F, (byte) 0xDD, (byte) 0x33, (byte) 0x57,
                (byte) 0x26, (byte) 0xF3, (byte) 0x10, (byte) 0xA0,
                (byte) 0xE9, (byte) 0x16, (byte) 0xA4, (byte) 0x2E,
                (byte) 0x9E, (byte) 0x15, (byte) 0x8E, (byte) 0xF4,
                (byte) 0x03, (byte) 0x04, (byte) 0xAA, (byte) 0x2C};
        // Another way is to calculate the HMAC key in a deterministic way
        // TODO: Do not use both HMAC keys. Choose one of them. You may keep the constant HMAC_KEY as a decoy.
        final byte[] CALCULATED_HMAC_KEY = TUPW.createHMACKey();
        // There are many other ways to create a HMAC key. Use you imagination.

        String encrypted = TUPW.encrypt("/home/wiktormalyska/aiprologProjekt.txt","Very1Very2Very3SécurePasswôrd?!", HMAC_KEY);
        System.out.println(encrypted);
        String decrypted = TUPW.decrypt(encrypted,"Very1Very2Very3SécurePasswôrd?!", HMAC_KEY);
        System.out.println(decrypted);
    }
}
