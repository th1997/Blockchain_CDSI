package com.blockchain;

import java.util.ArrayList;

    /**
     * Cette classe stocke une liste de blocks.
     */
public class Blockchain {

        private ArrayList<Block> blockChain = new ArrayList<>();
        private Block lastBlock;

        public Blockchain() { }

        /**
         * Constructeur qui prend une ArrayList de Block pour créer l’objet BlockChain.
         * @param BClist ArrayList qui contient tous les blocs de la chaîne nouvellement créée.
         */
        public Blockchain(ArrayList<Block> BClist) {
            blockChain = BClist;
        }

        /**
         * Accède a l'ArrayList blockChain.
         * @return ArrayList de Block representant la Blockchain
         */
        public ArrayList<Block> getChain() {
            return blockChain;
        }

        /**
         * Ajoute un bloc à l’ArrayList et stocke le dernier bloc de la chaîne.
         * @param block Block à ajouter à la classe.
         */
        public void addBlock(Block block) {
            blockChain.add(block);
            lastBlock = block;
        }

        /**
         * Retourne le hash du dernier Block de la chaine.
         * @return Le hash du Block précédent stocké dans la chain.
         */
        public String getLastBlockHash() {
            return lastBlock.getHash();
        }

        /**
         * Si l’objet est une Blockchain, son membre ArrayList est testé par rapport au membre actuel.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (!(o instanceof Blockchain otherChain)) { return false; }

            ArrayList<Block> otherChainList = otherChain.getChain();
            return otherChainList.equals(this.getChain());
        }

}
