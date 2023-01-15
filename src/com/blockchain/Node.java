package com.blockchain;

import java.util.ArrayList;

/**
 * Cette classe ajoute des Block dans la blockchain.
 */
public class Node {

    private Blockchain currentValidChain;
    private Blockchain proposedChain;
    private Block newBlock;
    private boolean chainValidated = false; //Initialisé a false lors de la création de l'objet.

    /**
     * Constructeur par défaut.
     */
    public Node() { }

    /**
     * Accède à la blockchain mise à jour actuellement proposée par le Noeud.
     * @return La blockchain actuelle proposée par le Noeud
     */
    public Blockchain getProposedBlockchain() {
        return this.proposedChain;
    }

    /**
     * Accède au booléen indiquant si le noeud a créé une chaîne valide.
     * @return Le booleen indicant si la chaine proposé est valide ou non.
     */
    public boolean isChainValidated() { return chainValidated; }

    /**
     * Met à jour la blockchain actuellement acceptée et le nouveau bloc miné afin que l’instance Node puisse être réutilisée.
     * @param currentBlockchain La blockchain valide actuelle.
     * @param minedBlock Le nouveau Block miné qui doit être vérifié par la Nodes.
     */
    public void updateNode(Blockchain currentBlockchain, Block minedBlock) {
        this.currentValidChain = new Blockchain(new ArrayList<>(currentBlockchain.getChain()));
        this.newBlock = minedBlock;
        this.createProposedChain();
        this.chainValidated = Node.validateBlockchain(this.proposedChain);
    }

    /**
     * Crée la proposition de nouvelle chaine à partir du nouveau block miné.
     */
    private void createProposedChain() {
        this.proposedChain = new Blockchain(this.currentValidChain.getChain());
        this.proposedChain.addBlock(this.newBlock);
    }

    /**
     * Méthode qui assure qu’un objet Blockchain est valide en vérifiant et en calculant les valeurs de hachage de chaque bloc.
     * @param proposedChain Un objet Blockchain qui n’a pas encore été validé.
     * @return Booléen indiquant la validité de la Blockchain.
     */
    public static boolean validateBlockchain(Blockchain proposedChain) {
        System.out.print("Validation de la Blockchain proposee...\r");
        ArrayList<Block> blockList = proposedChain.getChain();
        String lastHash = Constant.GENESIS_PREV_HASH;
        boolean valid = true;

        for (Block currentBlock : blockList) {
            String calculatedBlockHash = currentBlock.calculBlockHash();
            if (!(lastHash.equals(currentBlock.getPreviousHash())
                    && currentBlock.getHash().equals(calculatedBlockHash))) {
                valid = false;
                break;
            }
            lastHash = calculatedBlockHash;
        }
        return valid;
    }
}