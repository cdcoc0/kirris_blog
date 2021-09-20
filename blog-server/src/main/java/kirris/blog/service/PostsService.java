package kirris.blog.service;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import kirris.blog.domain.posts.PostsRequestDto;
import kirris.blog.domain.posts.PostsResponseDto;
import kirris.blog.exception.NotFoundException;
import kirris.blog.exception.UnauthorizedException;
import kirris.blog.repository.AuthRepository;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final AuthRepository authRepository;

    //==글 쓰기
    @Transactional
    public PostsResponseDto write(PostsRequestDto post, AuthResponseDto user) {

        //==작성자 정보 가져오기
        Auth userInfo = authRepository.findById(user.getId())
                .orElseThrow(() -> new UnauthorizedException("user not found"));

        //==html태그 검증, 태그 문자열 변경, 썸네일 추출
        post.sanitizeHtml();
        post.handleTags();
        post.extractThumbnail();

        //응답 DTO 반환
        return new PostsResponseDto(postsRepository.save(post, userInfo));
    }

    //==글 목록
    public List<PostsResponseDto> list(int page, String tag) {

        //==데이터 가져오기
        List<Posts> results = tag == null ? postsRepository.findAll(page): postsRepository.findAllByTag(page, tag); //없으면?
        List<PostsResponseDto> responseBody = results.stream().map(PostsResponseDto::new).collect(Collectors.toList());

        //==html태그 제거 및 내용 길이 제한, 태그 처리
        responseBody.forEach(post -> {
            post.removeHtmlAndShortenTitleAndBody();
            post.tagsToArray();
        });

        return responseBody;
    }

    //==글 읽기
    public PostsResponseDto read(long id) {

        //==글번호로 해당 게시글 가져오기
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        //==응답 dto에 저장, 태그 처리
        PostsResponseDto post = new PostsResponseDto(entity);
        post.tagsToArray();

        return post;
    }

    //==글 수정
    @Transactional
    public PostsResponseDto update(long id, PostsRequestDto post) {

        //==글번호로 게시글 찾기
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        //==태그 처리, 찾은 게시글 수정
        post.sanitizeHtml();
        post.extractThumbnail();
        post.handleTags();
        entity.update(post.getTitle(), post.getBody(), post.getHandledTags(), post.getThumbnail());

        return new PostsResponseDto(entity);
    }

    //==글 삭제
    @Transactional
    public void remove(long id) {

        postsRepository.delete(id);
    }
}
