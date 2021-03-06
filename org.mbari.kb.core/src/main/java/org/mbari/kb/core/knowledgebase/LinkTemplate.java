/*
 * @(#)ILinkTemplate.java   2008.12.30 at 01:50:53 PST
 *
 * Copyright 2007 MBARI
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 2.1
 * (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.mbari.kb.core.knowledgebase;

import org.mbari.kb.core.ILink;

/**
 *
 * @author brian
 */
public interface LinkTemplate extends KnowledgebaseObject, ILink {

    /**
     * @return
     */
    ConceptMetadata getConceptMetadata();


    /**
     * Returns a String concatenation of the <code>LinkTemplate</code>
     * attributes joined by DELIMITER. The format is
     * <pre>
     * linkName | toConcept | linkValue
     * </pre>
     *
     * @return String concatenation of the <code>LinkTemplate</code>
     * attributes joined by space characters.
     */
    String stringValue();
}
