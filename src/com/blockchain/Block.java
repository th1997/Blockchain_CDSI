package com.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

    /**
    * Classe qui crée les blocs qui enregistrent toutes les transactions dans la blockchain.
    * Ces données dans cette classe sont immuables
    */
public class Block {

    private String hash;
    private final String previousHash;
    private final String data;
    private final long timeStamp;
    private int nonce;

    /**
     * Constructeur du nouveau Block.
     * @param transactionData Données de transaction stockées dans le bloc et utilisées pour générer un hash unique.
     * @param previousBlockHash Hash SHA-256 du bloc précédent.
     */
    public Block(String transactionData, String previousBlockHash) {
        data = transactionData;
        previousHash = previousBlockHash;
        timeStamp = new Date().getTime(); //timeStamp sur l’heure actuelle.
        hash = calculBlockHash();
    }

    /**
     * Calcule la valeur de hash des blocs à l’aide de l’algorithme de hachage SHA-256.
     * @return Une chaîne unique de 32 caractères qui représente le hash de ce block.
     */
    public String calculBlockHash() {
        String dataToHash = previousHash + timeStamp + nonce + data;
        MessageDigest digest;
        byte[] bytes = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();
        assert bytes != null;

        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    /**
     * Mines un bloc en utilisant un nombre spécifié de zéros en tête requis dans le hachage.
     * @param prefix Le nombre de zéros requis dans le bloc de hachage
     * @return Le hash unique de 32 caractères conforme au préfixe
     */
    public String mineBlock(int prefix) {
        System.out.println("Minage du Block...");
        System.out.println("Hash du Block Initial : " + this.hash);
        System.out.println("Incrementation de la valeur Nonce...");
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculBlockHash();
        }
        System.out.println("Hash du Block miné : " + this.hash +'\n');
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block block)) return false;
        return getPreviousHash().equals(block.getPreviousHash());
    }

    public String getHash() { return hash; }

    public String getPreviousHash() { return previousHash; }

}
