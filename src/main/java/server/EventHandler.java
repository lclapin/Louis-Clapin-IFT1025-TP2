package server;

import java.io.IOException;

/**
 * L'interface EventHandler est une interface fonctionnelle definissant la methode handle.
 */
@FunctionalInterface
public interface EventHandler {
    /**
     * La methode handle sert de patron.
     * @param cmd represente la commande envoyee par le client.
     * @param arg represente l'argument envoye par le client.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    void handle(String cmd, String arg) throws IOException;
}