ALTER TABLE Concept DROP CONSTRAINT CncptPrntCncptIDFK
ALTER TABLE ConceptDelegate DROP CONSTRAINT CncptDlgteCncptDFK
ALTER TABLE ConceptName DROP CONSTRAINT CncptNameCncptIDFK
ALTER TABLE History DROP CONSTRAINT HstryCncptDlgtIDFK
ALTER TABLE LinkRealization DROP CONSTRAINT LnkRlzCncptDlgtDFK
ALTER TABLE LinkTemplate DROP CONSTRAINT LnkTmpCncptDlgtDFK
ALTER TABLE Media DROP CONSTRAINT MdCncptDlegateIDFK
ALTER TABLE Prefs DROP CONSTRAINT UNQ_Prefs_0
DROP INDEX idx_Concept_FK1
DROP INDEX idx_Concept_LUT
DROP TABLE Concept
DROP INDEX idx_ConceptDelegate_FK1
DROP INDEX idx_ConceptDelegate_LUT
DROP TABLE ConceptDelegate
DROP INDEX idx_ConceptName_name
DROP INDEX idx_ConceptName_FK1
DROP INDEX idx_ConceptName_LUT
DROP TABLE ConceptName
DROP INDEX idx_History_FK1
DROP INDEX idx_History_LUT
DROP TABLE History
DROP INDEX idx_LinkRealization_FK1
DROP INDEX idx_LinkRealization_LUT
DROP TABLE LinkRealization
DROP INDEX idx_LinkTemplate_FK1
DROP INDEX idx_LinkTemplate_LUT
DROP TABLE LinkTemplate
DROP INDEX idx_Media_FK1
DROP INDEX idx_Media_LUT
DROP TABLE Media
DROP TABLE UserAccount
DROP TABLE Prefs
DELETE FROM UniqueID WHERE TableName = 'UserName'
DELETE FROM UniqueID WHERE TableName = 'ConceptDelegate'
DELETE FROM UniqueID WHERE TableName = 'LinkRealization'
DELETE FROM UniqueID WHERE TableName = 'Concept'
DELETE FROM UniqueID WHERE TableName = 'History'
DELETE FROM UniqueID WHERE TableName = 'ConceptName'
DELETE FROM UniqueID WHERE TableName = 'Media'
DELETE FROM UniqueID WHERE TableName = 'LinkTemplate'
