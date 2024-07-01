package com.hana.api.challenge.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;



@ToString
@Getter
@Setter
public class InvitationListRequest {
    List<InvitationInfo> invitationList;
}
