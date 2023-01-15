package com.blockchain;

import java.util.Random;
import java.util.ArrayList;

/**
 * Cette classe stocke le reseau blockchain complet.
 * Elle comprend plusieurs noeuds qui extraient et vérifient les Block.
 */
public class Reseau {

    private static int transactionNumber = 0;
    private ArrayList<Mineur> miners = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private Blockchain NTChain = new Blockchain();

    /**
     * Constructeur par défaut. Ajoute 1 Mineur et 1 Node.
     */
    public Reseau() {
        this.addMiner();
        this.addNode();
    }

    /**
     * Crée un objet Mineur et l'ajoute à la liste de Mineurs.
     */
    public void addMiner() {
        Mineur newMiner = new Mineur();
        this.miners.add(newMiner);
    }

    /**
     * Crée une nouvelle Node et l'ajoute à la liste de Nodes.
     */
    public void addNode() {
        Node newNode = new Node();
        this.nodes.add(newNode);
    }

    /**
     * Cette méthode choisit un mineur au hasard dans la liste des mineurs pour extraire le plus récent bloc.
     * @return L'index du Mineur "gagnant".
     */
    private Mineur successfulMiner() {
        Random rand = new Random();
        int minerIndex = rand.nextInt(this.miners.size()) + 1;
        return this.miners.get(minerIndex);
    }

    /**
     * Simule une nouvelle transaction sur le Reseau.
     * @param transactionData Chaîne représentant les données de transaction.
     */
    public void newTransaction(String transactionData) {
        Reseau.transactionNumber++;
        System.out.println("-----------------------------------------------------------------------------\n"
                + "TRANSACTION #" + Reseau.transactionNumber + "\n"
                + "-----------------------------------------------------------------------------");
        Mineur transactionMiner = this.successfulMiner();

        System.out.println("Envoi des donnees de transaction a tous les mineurs sur le Reseau...\n");
        Reseau.delay(2000);
        if (this.NTChain.getChain().size() == 0) {
            //Cas pour le bloc genesis uniquement.
            transactionMiner.newTransaction(transactionData,Constant.GENESIS_PREV_HASH);
        } else {
            transactionMiner.newTransaction(transactionData, this.NTChain.getLastBlockHash());
        }

        Block newlyMinedBlock = transactionMiner.getNewestBlock();
        System.out.println("Block miné avec succès par Mineur " + transactionMiner.getUniqueID());

        if (nodeConsensus(newlyMinedBlock)) {
            //On suppose qu’il y a au moins 1 noeud dans le réseau.
            this.NTChain = this.nodes.get(0).getProposedBlockchain();
            System.out.println("""
                    Un consensus a ete trouve entre tous les noeuds Reseau : mise à jour de la chaîne Reseau
                    -----------------------------------------------------------------------------

                    """);
        } else {
            System.out.println("""
                    Le consensus n’a pas ete atteint : le Bloc a ete rejete
                    -----------------------------------------------------------------------------

                    """);
        }
    }

    /**
     * Permet d'initialiser les noeuds avec le nouveau block miné.
     * @param newlyMinedBlock Block nouvellement miné.
     */
    private void initNodes(Block newlyMinedBlock) {
        int nodeNum = 1;
        System.out.println("\nInitialisation des noeuds avec le nouveau bloc...");
        Reseau.delay(1500);
        for (Node n : this.nodes) {
            System.out.print("Node " + nodeNum + " : ");
            n.updateNode(this.NTChain, newlyMinedBlock);
            nodeNum++;
            Reseau.delay(1500);
        }
        System.out.println("\n");
    }

    /**
     * Simule le consensus de l’ArrayList de noeuds.
     * @param newlyMinedBlock Nouveau Block miné.
     * @return Resultat du Consensus (Validé ou Rejeté).
     */
    private boolean nodeConsensus(Block newlyMinedBlock) {
        boolean result = true;
        this.initNodes(newlyMinedBlock);
        Blockchain previousChain = this.nodes.get(0).getProposedBlockchain();
        System.out.println("Starting Node Consensus protocol...");

        for (int i = 0; i < this.nodes.size(); i++) {
            Reseau.delay(600);
            if (!(this.nodes.get(i).isChainValidated())) {
                result = false; //Si un noeud rejette le bloc
                break;
            } else {
                System.out.println("Node " + (i + 1) + " proposed chain is valid");
            }

            if (!(this.nodes.get(i).getProposedBlockchain().equals(previousChain))) {
                result = false;
                break;
            } else {
                System.out.println("Node " + (i + 1) + " a trouvé un consensus avec les Nœuds précédents");
            }
            System.out.print("\n");
            previousChain = this.nodes.get(i).getProposedBlockchain();
        }
        Reseau.delay(600);
        return result;
    }

    /**
     * Permet d'ajouter du retard au simulateur.
     * @param milliseconds Temps de retard souhaité, en millisecondes.
     */
    protected static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
