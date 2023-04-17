module Clapin_Louis {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens mvc to javafx.graphics, javafx.fxml;
}