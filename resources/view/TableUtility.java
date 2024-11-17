package view;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

public class TableUtility {

	/**
	 * This method sets text wrapping for a given table column.
	 * @param column the table column that will enable text wrapping
	 */
	public static <T> void setTextWrappingForColumn(TableColumn<T, String> column) {
		column.setCellFactory(col -> {
    		TableCell<T, String> cell = new TableCell<T, String>() {
    			private final Text text = new Text();
    			
    			@Override
    			protected void updateItem(String cellValue, boolean empty) {
    				super.updateItem(cellValue, empty);
    				if (empty || cellValue == null) {
    					setGraphic(null);
    				}
    				else {
    					text.setText(cellValue);
    					text.setWrappingWidth(column.getWidth());
    					setGraphic(text);
    				}
    			}
    		};
    		return cell;
    	});
	}
	
}
