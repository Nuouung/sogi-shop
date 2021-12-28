package toy.jinseokshop.fileUpload.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.member.Member;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MUSIC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Embedded
    private UploadFile attachMusic;
    @Embedded
    private List<UploadFile> musicAlbum;

    protected Music() {
    }

    public static Music createMusic(Member member, UploadFile attachMusic, List<UploadFile> musicAlbum) {
        Music music = new Music();
        music.setMember(member);
        music.setAttachMusic(attachMusic);
        music.setMusicAlbum(musicAlbum);
        return music;
    }
}
