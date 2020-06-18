CREATE TABLE Concept (id BIGINT NOT NULL, NodcCode VARCHAR(20), Originator VARCHAR(255), RankLevel VARCHAR(20), RankName VARCHAR(20), Reference VARCHAR(1024), StructureType VARCHAR(10), TaxonomyType VARCHAR(20), LAST_UPDATED_TIME TIMESTAMP, ParentConceptID_FK BIGINT, PRIMARY KEY (id))
CREATE INDEX idx_Concept_FK1 ON Concept (ParentConceptID_FK)
CREATE INDEX idx_Concept_LUT ON Concept (LAST_UPDATED_TIME)
CREATE TABLE ConceptDelegate (id BIGINT NOT NULL, LAST_UPDATED_TIME TIMESTAMP, ConceptID_FK BIGINT NOT NULL, PRIMARY KEY (id))
CREATE INDEX idx_ConceptDelegate_FK1 ON ConceptDelegate (ConceptID_FK)
CREATE INDEX idx_ConceptDelegate_LUT ON ConceptDelegate (LAST_UPDATED_TIME)
CREATE TABLE ConceptName (id BIGINT NOT NULL, Author VARCHAR(255), ConceptName VARCHAR(128) NOT NULL UNIQUE, NameType VARCHAR(10) NOT NULL, LAST_UPDATED_TIME TIMESTAMP, ConceptID_FK BIGINT, PRIMARY KEY (id))
CREATE INDEX idx_ConceptName_name ON ConceptName (ConceptName)
CREATE INDEX idx_ConceptName_FK1 ON ConceptName (ConceptID_FK)
CREATE INDEX idx_ConceptName_LUT ON ConceptName (LAST_UPDATED_TIME)
CREATE TABLE History (id BIGINT NOT NULL, Action VARCHAR(16), Approved SMALLINT, CreationDTG TIMESTAMP NOT NULL, CreatorName VARCHAR(50) NOT NULL, Field VARCHAR(2048), NewValue VARCHAR(2048), OldValue VARCHAR(2048), ProcessedDTG TIMESTAMP, ProcessorName VARCHAR(50), LAST_UPDATED_TIME TIMESTAMP, ConceptDelegateID_FK BIGINT, PRIMARY KEY (id))
CREATE INDEX idx_History_FK1 ON History (ConceptDelegateID_FK)
CREATE INDEX idx_History_LUT ON History (LAST_UPDATED_TIME)
CREATE TABLE LinkRealization (id BIGINT NOT NULL, LinkName VARCHAR(50), LinkValue VARCHAR(2048), ToConcept VARCHAR(128), LAST_UPDATED_TIME TIMESTAMP, ConceptDelegateID_FK BIGINT, PRIMARY KEY (id))
CREATE INDEX idx_LinkRealization_FK1 ON LinkRealization (ConceptDelegateID_FK)
CREATE INDEX idx_LinkRealization_LUT ON LinkRealization (LAST_UPDATED_TIME)
CREATE TABLE LinkTemplate (id BIGINT NOT NULL, LinkName VARCHAR(50), LinkValue VARCHAR(2048), ToConcept VARCHAR(128), LAST_UPDATED_TIME TIMESTAMP, ConceptDelegateID_FK BIGINT, PRIMARY KEY (id))
CREATE INDEX idx_LinkTemplate_FK1 ON LinkTemplate (ConceptDelegateID_FK)
CREATE INDEX idx_LinkTemplate_LUT ON LinkTemplate (LAST_UPDATED_TIME)
CREATE TABLE Media (id BIGINT NOT NULL, Caption VARCHAR(1000), Credit VARCHAR(255), PrimaryMedia SMALLINT DEFAULT 0, MediaType VARCHAR(5), LAST_UPDATED_TIME TIMESTAMP, Url VARCHAR(1024), ConceptDelegateID_FK BIGINT, PRIMARY KEY (id))
CREATE INDEX idx_Media_FK1 ON Media (ConceptDelegateID_FK)
CREATE INDEX idx_Media_LUT ON Media (LAST_UPDATED_TIME)
CREATE TABLE UserAccount (id BIGINT NOT NULL, Affiliation VARCHAR(512), Email VARCHAR(50), FirstName VARCHAR(50), LastName VARCHAR(50), Password VARCHAR(50) NOT NULL, Role VARCHAR(10) NOT NULL, LAST_UPDATED_TIME TIMESTAMP, UserName VARCHAR(50) NOT NULL UNIQUE, PRIMARY KEY (id))
CREATE TABLE Prefs (NodeName VARCHAR(1024) NOT NULL, PrefKey VARCHAR(256) NOT NULL, PrefValue VARCHAR(256) NOT NULL, PRIMARY KEY (NodeName, PrefKey))
ALTER TABLE Prefs ADD CONSTRAINT UNQ_Prefs_0 UNIQUE (NodeName, PrefKey)
ALTER TABLE Concept ADD CONSTRAINT CncptPrntCncptIDFK FOREIGN KEY (ParentConceptID_FK) REFERENCES Concept (id)
ALTER TABLE ConceptDelegate ADD CONSTRAINT CncptDlgteCncptDFK FOREIGN KEY (ConceptID_FK) REFERENCES Concept (id)
ALTER TABLE ConceptName ADD CONSTRAINT CncptNameCncptIDFK FOREIGN KEY (ConceptID_FK) REFERENCES Concept (id)
ALTER TABLE History ADD CONSTRAINT HstryCncptDlgtIDFK FOREIGN KEY (ConceptDelegateID_FK) REFERENCES ConceptDelegate (id)
ALTER TABLE LinkRealization ADD CONSTRAINT LnkRlzCncptDlgtDFK FOREIGN KEY (ConceptDelegateID_FK) REFERENCES ConceptDelegate (id)
ALTER TABLE LinkTemplate ADD CONSTRAINT LnkTmpCncptDlgtDFK FOREIGN KEY (ConceptDelegateID_FK) REFERENCES ConceptDelegate (id)
ALTER TABLE Media ADD CONSTRAINT MdCncptDlegateIDFK FOREIGN KEY (ConceptDelegateID_FK) REFERENCES ConceptDelegate (id)
CREATE TABLE UniqueID (TableName VARCHAR(50) NOT NULL, NextID DECIMAL(15), PRIMARY KEY (TableName))
INSERT INTO UniqueID(TableName, NextID) values ('History', 0)
INSERT INTO UniqueID(TableName, NextID) values ('Concept', 0)
INSERT INTO UniqueID(TableName, NextID) values ('LinkRealization', 0)
INSERT INTO UniqueID(TableName, NextID) values ('LinkTemplate', 0)
INSERT INTO UniqueID(TableName, NextID) values ('ConceptName', 0)
INSERT INTO UniqueID(TableName, NextID) values ('Media', 0)
INSERT INTO UniqueID(TableName, NextID) values ('ConceptDelegate', 0)
INSERT INTO UniqueID(TableName, NextID) values ('UserName', 0)
