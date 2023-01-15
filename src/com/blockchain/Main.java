package com.blockchain;

import java.util.Scanner;

/**
 * Classe utilisée pour réaliser une démonstration de la blockchain.
 */
public class Main {

    /**
     * Classe principale pour la blockchain.
     * @param args Arguments ligne de commande
     */
    public static void main(String[] args) {
        Reseau blockchainNT = new Reseau();
        boolean validInput = false, inputInvalid;
        System.out.println("Simulation d'une BlockChain\n");

        Scanner sc = new Scanner(System.in);

        do {
            inputInvalid = false;
            int nodeNum = 0;
            System.out.print("\nCombien de Nodes voulez vous sur le Reseau ? ");
            try {
                nodeNum = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                inputInvalid = true;
                nodeNum = -1;
            } finally {
                if (nodeNum < 1 || inputInvalid) {
                    System.out.println("""

                            Veuillez entrer une valeur positive !\s
                            Le reseau a besoin de plusieurs Nodes pour fonctionner.""");
                } else {
                    validInput = true;
                    for (int i = 1; i < nodeNum; i++) {
                        blockchainNT.addNode();
                    }
                }
            }
        } while (!validInput);

        validInput = false;

        do {
            inputInvalid = false;
            int minerNum = 0;
            System.out.print("\nCombien de Mineurs voulez vous sur le Reseau ? ");
            try {
                minerNum = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                inputInvalid = true;
                minerNum = -1;
            } finally {
                if (minerNum < 1 || inputInvalid) {
                    System.out.println("""

                            Veuillez entrer une valeur positive !\s
                            Le reseau a besoin de plusieurs Mineurs pour fonctionner.""");
                } else {
                    validInput = true;
                    for (int i = 1; i < minerNum; i++) {
                        blockchainNT.addMiner();
                    }
                }
            }
        } while (!validInput);

        while (true) {
            System.out.println("""

                    Entrer la data  que vous voulez utiliser pour creer une nouvelle transaction.
                    ou entrer 'exit' pour quitter le programme
                    """);
            String inputString = sc.nextLine();
            if (inputString.equalsIgnoreCase("exit")) {
                System.out.println("\nSimulation terminee.");
                break;
            } else {
                blockchainNT.newTransaction(inputString);
            }
        }
        sc.close();
    }
}
