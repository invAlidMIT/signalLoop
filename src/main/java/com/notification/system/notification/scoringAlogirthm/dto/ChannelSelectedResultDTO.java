package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.user.enums.Channel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChannelSelectedResultDTO {

    private Channel selectedChannel;
    private double emailScore;
    private double smsScore;
    private double pushScore;
}
