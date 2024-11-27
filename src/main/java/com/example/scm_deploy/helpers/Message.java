package com.scm.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String content;
    @Builder.Default
    private com.scm.helpers.MessageType type = com.scm.helpers.MessageType.blue;
    public String type() {
        return type.name().toLowerCase(); // Return type as lowercase string for Tailwind classes
    }
}