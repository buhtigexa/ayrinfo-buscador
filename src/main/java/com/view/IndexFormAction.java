package com.view;

import javax.swing.JFileChooser;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class IndexFormAction extends ActionSupport implements ModelDriven<Object> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 934905583372831339L;
	private IndexForm indexForm = new IndexForm();

	private String getNameDirectory() {
		JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setDialogTitle("Seleccione el Directorio");
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		directoryChooser.setAcceptAllFileFilterUsed(false);
		directoryChooser.setOpaque(false);
		if(directoryChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			return(directoryChooser.getSelectedFile().toString());
		return null;
	}
	
    public IndexFormAction() {
    }

    public Object getModel() {
        return indexForm;
    }

    public String execute() {
        return SUCCESS;
    }

    public IndexForm getIndexForm() {
        return indexForm;
    }

    public void setIndexForm(IndexForm indexForm) {
        this.indexForm = indexForm;
    }
    
    public String dataDir() {
    	this.indexForm.setDataDir(this.getNameDirectory());
		return SUCCESS;
	}

	public String indexDir() {
		this.indexForm.setIndexDir(this.getNameDirectory());
		return SUCCESS;
	}
}