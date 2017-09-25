package fr.polytech.unice.esb.services.travel.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.unice.esb.services.travel.actions.DocumentAction;
import fr.polytech.unice.esb.services.travel.actions.list.ListAction;
import fr.polytech.unice.esb.services.travel.actions.list.SubmitAction;
import org.json.JSONObject;

import javax.ejb.Singleton;
import javax.enterprise.inject.spi.CDI;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ActionComponentImpl implements ActionComponent {

    private Map<String, Class<? extends DocumentAction>> actions;
    private ObjectMapper mapper;

    public ActionComponentImpl() {
        actions = new HashMap<>();
        actions.put("submit", SubmitAction.class);
        actions.put("list", ListAction.class);
        mapper = new ObjectMapper();
        // Allow unknown properties
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Optional<DocumentAction<?, ?>> get(String actionName) {
        Class<? extends DocumentAction> action = actions.get(actionName);
        if (action == null) {
            return Optional.empty();
        } else {
            return Optional.of(CDI.current().select(action).get());
        }
    }

    @Override
    public <I,O> Object execute(DocumentAction<I, O> documentAction, Map<String, Object> input) throws IOException {
        Class<I> clazz = (Class<I>) inputTypeOf(documentAction);
        I parsedInput = mapper.readValue(new JSONObject(input).toString(), clazz);
        return documentAction.execute(parsedInput);
    }

    /**
     * Get the input class of a document action
     * @param documentAction
     * @param <I>
     * @param <O>
     * @return
     */
    private <I, O> Class<?> inputTypeOf(DocumentAction<I, O> documentAction) {
        return documentAction.getInputType();
    }
}
