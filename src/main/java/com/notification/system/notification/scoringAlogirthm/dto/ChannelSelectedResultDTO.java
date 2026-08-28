package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ChannelSelectedResultDTO {

    private Channel selectedChannel;
    private double emailScore;
    private double smsScore;
    private double pushScore;
}
