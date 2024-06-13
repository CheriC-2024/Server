package com.art.cheric.dto.art.respond;

import com.art.cheric.entity.ArtistArt;
import lombok.Getter;

@Getter
public class ArtistArtCreateResponseDto {
    private ArtCreateResponseDto art;
    private String artistDescription;
    private String copyright;
    private String notice;

    public ArtistArtCreateResponseDto of(ArtistArt artistArt) {
        ArtCreateResponseDto artCreateResponseDto = new ArtCreateResponseDto();
        this.art = artCreateResponseDto.of(artistArt.getArt());
        this.artistDescription = artistArt.getArtistDescription();
        this.copyright = artistArt.getCopyright();
        this.notice = artistArt.getNotice();

        return this;
    }
}
