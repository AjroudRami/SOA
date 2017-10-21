package fr.polytech.unice.esb.services.report.components;


import fr.polytech.unice.esb.services.report.actions.DocumentAction;

import java.util.Map;
import java.util.Optional;

/**
 * A component that handles actions on a given document
 */
public interface ActionComponent {

    /**
     * Gat an action from its name
     * @param actionName
     * @return
     */
    Optional<DocumentAction<?, ?>> get(String actionName);

    /**
     * Executes a given action for a given input
     * @param documentAction
     * @param input
     * @return
     */
    <I, O> Object execute(DocumentAction<I, O> documentAction, Map<String, Object> input) throws Exception;
}
