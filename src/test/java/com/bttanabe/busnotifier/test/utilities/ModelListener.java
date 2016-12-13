package com.bttanabe.busnotifier.test.utilities;

import com.btanabe.busnotifier.model.Model;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 12/12/16.
 */
@Getter
public class ModelListener {
    private List<Model> collectedModels = new ArrayList<>();

    @Subscribe
    public void publishedModel(final Model model) {
        collectedModels.add(model);
    }
}
