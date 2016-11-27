package com.btanabe.busnotifier.model.types;

import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class ServiceAlert {
    private String id;
    private long creationTime;
//    private List<TimeRangeBean> activeWindows;
//    private List<TimeRangeBean> publicationWindows;
    private String reason;
//    private List<NaturalLanguageStringBean> summaries;
//    private List<NaturalLanguageStringBean> descriptions;
//    private List<NaturalLanguageStringBean> urls;
//    private List<SituationAffectsBean> allAffects;
//    private List<SituationConsequenceBean> consequences;
//    private ESeverity severity;
}
