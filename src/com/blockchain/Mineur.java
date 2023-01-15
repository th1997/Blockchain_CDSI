package com.blockchain;

import java.util.UUID;

/**
 * Cette classe représente quelqu’un qui essaie d’extraire un bloc et de l’ajouter à la chaîne.
 */
public class Mineur {

    private Block minedBlock;
    private final String uniqueID;

    public Mineur() { uniqueID = UUID.randomUUID().toString(); }

    /**
     * Crée un objet Block qui n’a pas encore été miné.
     * @param transactionData La chaîne de données de transaction nécessaire pour créer un nouveau bloc.
     * @param previousBlockHash Hash du Block précédent.
     */
    public void newTransaction(String transactionData, String previousBlockHash) {
        this.minedBlock = new Block(transactionData, previousBlockHash);
    }

    /**
     * Cette fonction retourne le bloc nouvellement créé SEULEMENT après l’exécution de la méthode mineBlock().
     * @return Le nouveau Bloc miné à ajouter à la Blockchain.
     */
    public Block getNewestBlock() {
        this.minedBlock.mineBlock(Constant.DIFFICULTY);
        return this.minedBlock;
    }

    /**
     * Permet de récuperer l'ID du Mineur.
     * @return L'ID du Mineur.
     */
    public String getUniqueID() {
        return uniqueID;
    }
}
