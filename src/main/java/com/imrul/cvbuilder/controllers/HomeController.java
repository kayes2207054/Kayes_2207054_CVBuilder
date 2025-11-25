package com.imrul.cvbuilder.controllers;

import com.imrul.cvbuilder.CVBuilderApplication;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void handleCreateNewCV() {
        CVBuilderApplication.getInstance().showCreateCVView();
    }

    @FXML
    private void handleViewSavedCVs() {
        CVBuilderApplication.getInstance().showSavedCVsView();
    }
}
