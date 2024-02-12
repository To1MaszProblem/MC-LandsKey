package pl.to1maszproblem.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import pl.to1maszproblem.notice.Notice;
import pl.to1maszproblem.notice.NoticeType;

@Getter
public class MessageConfiguration extends OkaeriConfig {
    private Notice teleported = new Notice(NoticeType.SUBTITLE ,"&aZostałeś przeteleportowany do krainy [land-name]!");
    private Notice doesntHaveKey = new Notice(NoticeType.TITLE ,"&cNie masz klucza do tej krainy");
    private String holdItemToCreate = "&cMusisz trzymać item w ręce aby ustawić klucz do krainy!";
    private String landAlreadyExist = "&cKraina o takiej nazwie już istnieje!";
    private String createdLand = "&aPomyslnie stworzono kraine o nazwie [land-name]!";
    private String landDoesntExist = "&cKraina o takiej nazwie nie istnieje!";
    private String landKeyDoesntSeted = "&cKlucz do tej krainy nie został ustawiony!";
    private String landLocationDoesntSeted = "&cLokacja do tej krainy nie został ustawiony!";
    private String deletedLand = "&aPomyslnie usunięto kraine o nazwie [land-name]!";

}