package com.jcnetwork.members.model.ui;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Toast {

    private String title;
    private String body;
    @NonNull
    private String type;
}
