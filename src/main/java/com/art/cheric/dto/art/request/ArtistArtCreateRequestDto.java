package com.art.cheric.dto.art.request;

import com.art.cheric.entity.Art;
import com.art.cheric.entity.ArtistArt;
import com.art.cheric.entity.User;
import lombok.Getter;

@Getter
public class ArtistArtCreateRequestDto {
    private ArtCreateRequestDto art;
    private String artistDescription;
    private String copyright;
    private String notice;

    // TODO: 추후 modelmapper class 로 변경하기 > builder 로 하니까 mapping이 제대로 되지 않나봐..
    public ArtistArt createArtistArt(User artist) {
        Art newArt = this.art.createArt();

        return ArtistArt.builder()
                .art(newArt)
                .user(artist)
                .artistDescription(this.artistDescription)
                .copyright(this.copyright)
                .notice(this.notice)
                .build();
    }

    public ArtistArtCreateRequestDto of(ArtistArt artistArt) {
        ArtCreateRequestDto artCreateRequestDto = new ArtCreateRequestDto();
        this.art = artCreateRequestDto.of(artistArt.getArt());
        this.artistDescription = artistArt.getArtistDescription();
        this.copyright = artistArt.getCopyright();
        this.notice = artistArt.getNotice();

        return this;
    }
}
