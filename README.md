# TUPW

Safely store credentials in config files

This program serves as an example of how to safely store credentials in config files. It works as a command line tool to encrypt and decrypt credentials. The decryption part can be incorporated into an application.

The idea is to store credentials in an config file in an encrypted form like this:

    <credentials>
      <user name="dbuser" user="1$Hl47qN6kiyziv4gsNM+bbQ==$MzKu+69m6ZmmGQty3PVcag==" password="1$wF31CPLL8F8KAEtKxOUjnw==$Ec7xItCNrdEd14rvG4oRO53CB9ZTthCWgZfcY2nzKFk="/>
    </credentials >

The data consists of three parts separated by '$' characters:

1. The format code. 1=`{IV}{AES-128-CBC-PKCS5Padding}`
2. The IV
3. The AES-128 encrypted data

So these data specify the type of encryption ([AES](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard "AES") 128 bit in [CBC](https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#CBC "CBC") mode with [PKCS5](https://tools.ietf.org/html/rfc2898 "PKCS5") padding) and the value of the initialization vector used for encryption. But where does the encryption key come from?

The encryption key is generated by calculating the [HMAC](https://en.wikipedia.org/wiki/Hash-based_message_authentication_code "HMAC")-[SHA-256](https://en.wikipedia.org/wiki/SHA-2 "SHA-256") value of a file that is filled with random bytes. This file should be stored in a directory that is only accessible by the user that runs the application and nobody else.

The HMAC needs a key that is hard-coded in the program. So the key is calculated from something that is "known" (the HMAC key) and something that is in "posession" of the program (the file).

The above data can only be decrypted with the same file and the same HMAC key.

The program is used like this ('d:\keyfile.bin' is the name of the key file):

    java -jar tupw.jar encrypt d:\keyfile.bin dbUser TrulyBadPassword;-)

This generates the following output:

    User = 1$Hl47qN6kiyziv4gsNM+bbQ==$MzKu+69m6ZmmGQty3PVcag==

    Password = 1$wF31CPLL8F8KAEtKxOUjnw==$Ec7xItCNrdEd14rvG4oRO53CB9ZTthCWgZfcY2nzKFk=

Of course, one would need the keyfile to decrypt this like so:

    java -jar tupw.jar decrypt d:\keyfile.bin "1$Hl47qN6kiyziv4gsNM+bbQ==$MzKu+69m6ZmmGQty3PVcag==" "1$wF31CPLL8F8KAEtKxOUjnw==$Ec7xItCNrdEd14rvG4oRO53CB9ZTthCWgZfcY2nzKFk="

which yields (with the correct key file):

    User = 'dbUser'
    Password = 'TrulyBadPassword;-)'
    
This way one can store the credentials and the key file in configuration management systems without storing them in the clear.

Of course, this is not perfectly safe, as an attacker can get access to the machine and extract the key file and the program classes and reverse engineer the way the key is calculated.

This program just makes it harder to get at the credentials, as both the file and the program code are needed to reconstruct the encryption key.
