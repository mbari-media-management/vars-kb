/*
Script generated by Aqua Data Studio 10.0.0 on Jan-08-2013 09:42:12 AM
Database: VARS
Schema: dbo
Objects: TABLE, VIEW, INDEX
*/
ALTER TABLE VideoFrame
    DROP CONSTRAINT VideoFrame_FK1
GO
ALTER TABLE VideoArchive
    DROP CONSTRAINT VideoArchive_FK1
GO
ALTER TABLE PhysicalData
    DROP CONSTRAINT PhysicalData_FK1
GO
ALTER TABLE Observation
    DROP CONSTRAINT Observation_FK1
GO
ALTER TABLE CameraPlatformDeployment
    DROP CONSTRAINT CameraDeployment_FK1
GO
ALTER TABLE CameraData
    DROP CONSTRAINT CameraData_FK1
GO
ALTER TABLE Association
    DROP CONSTRAINT Association_FK1
GO
ALTER TABLE VideoArchive
    DROP CONSTRAINT uc_VideoArchiveName
GO
ALTER TABLE PhysicalData
    DROP CONSTRAINT uc_PhysicalData_FK1
GO
ALTER TABLE CameraData
    DROP CONSTRAINT uc_VideoFrameID_FK
GO
DROP INDEX VideoFrame.idx_VideoFrame_LUT
GO
DROP INDEX VideoFrame.idx_VideoFrame_FK1
GO
DROP INDEX VideoArchive.idx_VideoArchive_name
GO
DROP INDEX VideoArchive.idx_VideoArchive_id
GO
DROP INDEX VideoArchive.idx_VideoArchive_LUT
GO
DROP INDEX VideoArchive.idx_VideoArchive_FK1
GO
DROP INDEX VideoArchiveSet.idx_VideoArchiveSet_id
GO
DROP INDEX VideoArchiveSet.idx_VideoArchiveSet_LUT
GO
DROP INDEX PhysicalData.idx_PhysicalData_LUT
GO
DROP INDEX PhysicalData.idx_PhysicalData_FK1
GO
DROP INDEX Observation.idx_Observation_LUT
GO
DROP INDEX Observation.idx_Observation_FK1
GO
DROP INDEX Observation.idx_Observation_ConceptName
GO
DROP INDEX CameraPlatformDeployment.idx_CameraPlatformDeployment_LUT
GO
DROP INDEX CameraPlatformDeployment.idx_CameraDeployment_FK1
GO
DROP INDEX CameraData.idx_CameraData_LUT
GO
DROP INDEX CameraData.idx_CameraData_FK1
GO
DROP INDEX Association.idx_Association_ToConcept
GO
DROP INDEX Association.idx_Association_PK
GO
DROP INDEX Association.idx_Association_LUT
GO
DROP INDEX Association.idx_Association_FK1
GO
DROP VIEW Annotations
GO
DROP TABLE VideoFrame
GO
DROP TABLE VideoArchiveSet
GO
DROP TABLE VideoArchive
GO
DROP TABLE UniqueID
GO
DROP TABLE PhysicalData
GO
DROP TABLE Observation
GO
DROP TABLE CameraPlatformDeployment
GO
DROP TABLE CameraData
GO
DROP TABLE Association
GO

CREATE TABLE Association  ( 
    id                  bigint NOT NULL,
    ObservationID_FK    bigint NULL,
    LinkName            varchar(128) NULL,
    ToConcept           varchar(50) NULL,
    LinkValue           varchar(1024) NULL,
    LAST_UPDATED_TIME   datetime NULL,
    CONSTRAINT Association_PK PRIMARY KEY NONCLUSTERED(id)
)
GO
CREATE TABLE CameraData  ( 
    id                  bigint NOT NULL,
    VideoFrameID_FK     bigint NULL,
    Name                varchar(50) NULL,
    Direction           varchar(50) NULL,
    Zoom                int NULL,
    Focus               int NULL,
    Iris                int NULL,
    FieldWidth          float NULL,
    StillImageURL       varchar(1024) NULL,
    LogDTG              datetime NULL,
    LAST_UPDATED_TIME   datetime NULL,
    X                   float NULL,
    Y                   float NULL,
    Z                   float NULL,
    PITCH               float NULL,
    ROLL                float NULL,
    XYUNITS             varchar(50) NULL,
    ZUNITS              varchar(50) NULL,
    HEADING             float NULL,
    VIEWHEIGHT          float NULL,
    VIEWWIDTH           float NULL,
    VIEWUNITS           varchar(50) NULL,
    CONSTRAINT CameraData_PK PRIMARY KEY CLUSTERED(id)

WITH (ALLOW_ROW_LOCKS = OFF)
    )
GO
CREATE TABLE CameraPlatformDeployment  ( 
    id                      bigint NOT NULL,
    VideoArchiveSetID_FK    bigint NULL,
    SeqNumber               int NULL,
    ChiefScientist          varchar(50) NULL,
    UsageStartDTG           datetime NULL,
    UsageEndDTG             datetime NULL,
    LAST_UPDATED_TIME       datetime NULL,
    CONSTRAINT CameraDeployment_PK PRIMARY KEY NONCLUSTERED(id)
)
GO
CREATE TABLE Observation  ( 
    id                  bigint NOT NULL,
    VideoFrameID_FK     bigint NULL,
    ObservationDTG      datetime NULL,
    Observer            varchar(50) NULL,
    ConceptName         varchar(50) NULL,
    Notes               varchar(200) NULL,
    LAST_UPDATED_TIME   datetime NULL,
    X                   float NULL,
    Y                   float NULL,
    CONSTRAINT Observation_PK PRIMARY KEY CLUSTERED(id)
)
GO
CREATE TABLE PhysicalData  ( 
    id                  bigint NOT NULL,
    VideoFrameID_FK     bigint NULL,
    Depth               real NULL,
    Temperature         real NULL,
    Salinity            real NULL,
    Oxygen              real NULL,
    Light               real NULL,
    Latitude            float NULL,
    Longitude           float NULL,
    LogDTG              datetime NULL,
    LAST_UPDATED_TIME   datetime NULL,
    Altitude            real NULL,
    CONSTRAINT PhysicalData_PK PRIMARY KEY NONCLUSTERED(id)
)
GO
CREATE TABLE UniqueID  ( 
    tablename   varchar(200) NOT NULL,
    nextid      bigint NULL 
    )
GO
CREATE TABLE VideoArchive  ( 
    id                      bigint NOT NULL,
    VideoArchiveSetID_FK    bigint NULL,
    TapeNumber              smallint NULL,
    StartTimeCode           varchar(11) NULL,
    videoArchiveName        varchar(1024) NULL,
    LAST_UPDATED_TIME       datetime NULL,
    CONSTRAINT VideoArchive_PK PRIMARY KEY CLUSTERED(id)
)
GO
CREATE TABLE VideoArchiveSet  ( 
    id                  bigint NOT NULL,
    TrackingNumber      varchar(7) NULL,
    ShipName            varchar(32) NULL,
    PlatformName        varchar(32) NULL,
    FormatCode          varchar(2) NULL,
    StartDTG            datetime NULL,
    EndDTG              datetime NULL,
    LAST_UPDATED_TIME   datetime NULL,
    CONSTRAINT VideoArchiveSet_PK PRIMARY KEY CLUSTERED(id)
)
GO
CREATE TABLE VideoFrame  ( 
    id                  bigint NOT NULL,
    RecordedDtg         datetime NULL,
    TapeTimeCode        varchar(11) NULL,
    HDTimeCode          varchar(11) NULL,
    InSequence          bit NULL,
    Displacer           varchar(50) NULL,
    DisplaceDtg         datetime NULL,
    VideoArchiveID_FK   bigint NULL,
    LAST_UPDATED_TIME   datetime NULL,
    CONSTRAINT VideoFrame_PK PRIMARY KEY CLUSTERED(id)
)
GO
CREATE VIEW Annotations
AS 
SELECT TOP 100 PERCENT 
    obs.ObservationDTG AS ObservationDate,
    obs.Observer,
    obs.ConceptName,
    obs.Notes,
    obs.X AS XPixelInImage,
    obs.Y AS yPixelInImage,
    vf.TapeTimeCode,
    vf.RecordedDtg AS RecordedDate ,
    va.videoArchiveName,
    vas.TrackingNumber,
    vas.ShipName,
    vas.PlatformName AS RovName,
    vas.FormatCode AS AnnotationMode,
    cpd.SeqNumber AS DiveNumber,
    cpd.ChiefScientist,
    cd.NAME AS CameraName,
    cd.Direction AS CameraDirection,
    cd.Zoom,
    cd.Focus,
    cd.Iris,
    cd.FieldWidth,
    cd.StillImageURL AS Image,
    cd.X AS CameraX,
    cd.Y AS CameraY,
    cd.Z AS CameraZ,
    cd.Pitch AS CameraPitchRadians,
    cd.Roll AS CameraRollRadians,
    cd.Heading AS CameraHeadingRadians,
    cd.XYUnits AS CameraXYUnits,
    cd.ZUnits AS CameraZUnits,
    cd.VIEWWIDTH as CameraViewWidth,
    cd.ViewHeight AS CameraViewHeight,
    cd.ViewUnits AS CameraViewUnits,
    pd.DEPTH,
    pd.Temperature,
    pd.Salinity,
    pd.Oxygen,
    pd.Light,
    pd.Latitude,
    pd.Longitude,
    obs.id AS ObservationID_FK,
    ass.id AS AssociationID_FK,
    ass.LinkName,
    ass.ToConcept,
    ass.LinkValue,
    ass.LinkName + ' | ' + ass.ToConcept + ' | ' + ass.LinkValue AS Associations,
    vf.HDTimeCode AS HighdefTimeCode,
    expd.IsNavigationEdited,
    expd.IsMerged,
    vf.id AS VideoFrameID_FK,
    pd.id AS PhysicalDataID_FK,
    cd.id AS CameraDataID_FK,
    va.id AS VideoArchiveID_FK,
    vas.id AS VideoArchiveSetID_FK 
FROM 
    Association ass 
    RIGHT JOIN Observation obs ON ass.ObservationID_FK = obs.id
    RIGHT JOIN VideoFrame vf ON obs.VideoFrameID_FK = vf.id 
    RIGHT JOIN VideoArchive va ON vf.VideoArchiveID_FK = va.id
    RIGHT JOIN VideoArchiveSet vas ON va.VideoArchiveSetID_FK = vas.id
    LEFT JOIN CameraPlatformDeployment cpd ON cpd.VideoArchiveSetID_FK = vas.id  
    LEFT JOIN CameraData cd ON cd.VideoFrameID_FK = vf.id 
    LEFT JOIN PhysicalData pd ON pd.VideoFrameID_FK = vf.id
    LEFT JOIN EXPDMergeStatus expd ON expd.VideoArchiveSetID_FK = vas.id  
WHERE
    obs.ConceptName IS NOT NULL
GO
CREATE NONCLUSTERED INDEX idx_Association_FK1
    ON Association(ObservationID_FK)
GO
CREATE NONCLUSTERED INDEX idx_Association_LUT
    ON Association(LAST_UPDATED_TIME)
GO
CREATE NONCLUSTERED INDEX idx_Association_PK
    ON Association(id)
GO
CREATE NONCLUSTERED INDEX idx_Association_ToConcept
    ON Association(ToConcept)
GO
CREATE NONCLUSTERED INDEX idx_CameraData_FK1
    ON CameraData(VideoFrameID_FK)
GO
CREATE NONCLUSTERED INDEX idx_CameraData_LUT
    ON CameraData(LAST_UPDATED_TIME)
GO
CREATE NONCLUSTERED INDEX idx_CameraDeployment_FK1
    ON CameraPlatformDeployment(VideoArchiveSetID_FK)
GO
CREATE NONCLUSTERED INDEX idx_CameraPlatformDeployment_LUT
    ON CameraPlatformDeployment(LAST_UPDATED_TIME)
GO
CREATE NONCLUSTERED INDEX idx_Observation_ConceptName
    ON Observation(ConceptName)
GO
CREATE NONCLUSTERED INDEX idx_Observation_FK1
    ON Observation(VideoFrameID_FK)
GO
CREATE NONCLUSTERED INDEX idx_Observation_LUT
    ON Observation(LAST_UPDATED_TIME)
GO
CREATE NONCLUSTERED INDEX idx_PhysicalData_FK1
    ON PhysicalData(VideoFrameID_FK)
GO
CREATE NONCLUSTERED INDEX idx_PhysicalData_LUT
    ON PhysicalData(LAST_UPDATED_TIME)
GO
CREATE NONCLUSTERED INDEX idx_VideoArchiveSet_LUT
    ON VideoArchiveSet(LAST_UPDATED_TIME)
GO
CREATE UNIQUE NONCLUSTERED INDEX idx_VideoArchiveSet_id
    ON VideoArchiveSet(id)
GO
CREATE NONCLUSTERED INDEX idx_VideoArchive_FK1
    ON VideoArchive(VideoArchiveSetID_FK)
GO
CREATE NONCLUSTERED INDEX idx_VideoArchive_LUT
    ON VideoArchive(LAST_UPDATED_TIME)
GO
CREATE UNIQUE NONCLUSTERED INDEX idx_VideoArchive_id
    ON VideoArchive(id)
GO
CREATE NONCLUSTERED INDEX idx_VideoArchive_name
    ON VideoArchive(videoArchiveName)
GO
CREATE NONCLUSTERED INDEX idx_VideoFrame_FK1
    ON VideoFrame(VideoArchiveID_FK)
GO
CREATE NONCLUSTERED INDEX idx_VideoFrame_LUT
    ON VideoFrame(LAST_UPDATED_TIME)
GO

ALTER TABLE CameraData
    ADD CONSTRAINT uc_VideoFrameID_FK
    UNIQUE (VideoFrameID_FK)
GO
ALTER TABLE PhysicalData
    ADD CONSTRAINT uc_PhysicalData_FK1
    UNIQUE CLUSTERED (VideoFrameID_FK)
GO
ALTER TABLE VideoArchive
    ADD CONSTRAINT uc_VideoArchiveName
    UNIQUE (videoArchiveName)
GO
ALTER TABLE Association
    ADD CONSTRAINT Association_FK1
    FOREIGN KEY(ObservationID_FK)
    REFERENCES Observation(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
    NOT FOR REPLICATION 
GO
ALTER TABLE Association
    NOCHECK CONSTRAINT Association_FK1
GO
ALTER TABLE CameraData
    ADD CONSTRAINT CameraData_FK1
    FOREIGN KEY(VideoFrameID_FK)
    REFERENCES VideoFrame(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
    NOT FOR REPLICATION 
GO
ALTER TABLE CameraPlatformDeployment
    ADD CONSTRAINT CameraDeployment_FK1
    FOREIGN KEY(VideoArchiveSetID_FK)
    REFERENCES VideoArchiveSet(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
GO
ALTER TABLE Observation
    ADD CONSTRAINT Observation_FK1
    FOREIGN KEY(VideoFrameID_FK)
    REFERENCES VideoFrame(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
    NOT FOR REPLICATION 
GO
ALTER TABLE PhysicalData
    ADD CONSTRAINT PhysicalData_FK1
    FOREIGN KEY(VideoFrameID_FK)
    REFERENCES VideoFrame(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
    NOT FOR REPLICATION 
GO
ALTER TABLE VideoArchive
    ADD CONSTRAINT VideoArchive_FK1
    FOREIGN KEY(VideoArchiveSetID_FK)
    REFERENCES VideoArchiveSet(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
    NOT FOR REPLICATION 
GO
ALTER TABLE VideoFrame
    ADD CONSTRAINT VideoFrame_FK1
    FOREIGN KEY(VideoArchiveID_FK)
    REFERENCES VideoArchive(id)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION 
    NOT FOR REPLICATION 
GO
