Framework:
JavaFx: 
	http://marketplace.eclipse.org/content/efxclipse


Import:
	File->Import->Existing Projects into Workspace->Select archive file->LaTazza.zip
	Project->Properties->Java Build Path->Libraries->JRE->Edit->Use Workspace Default
	
	Files coded in utf-8 (Window->Preferences->General->Workspace->UTF-8)

Componenti gruppo:
-Magno Alessandro matricola 4478234

Esecuzione progetto rifattorizzato:
-importare il progetto in Eclipse

-aggiungere le Librerie esterne allegati: tasto destro sul progetto -> Build Path ->
					  Add External Archives -> selezionarli

-Run -> Run Configurations -> Arguments -> VM arguments
	--module-path "percorso alle cartella contenente le librerie javafx" 
	--add-modules=javafx.controls,javafx.fxml





