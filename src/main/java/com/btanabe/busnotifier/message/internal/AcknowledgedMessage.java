package com.btanabe.busnotifier.message.internal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 12/23/16.
 */
@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class AcknowledgedMessage {

    @NonNull
    private final InternalMessage acknowledgedMessage;
}
