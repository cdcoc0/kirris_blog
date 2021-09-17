import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import Button from "../common/Button";
import Responsive from "../common/Responsive";
import SubInfo from "../common/SubInfo";
import Tags from "../common/Tags";

const PostListBlock = styled(Responsive)`
    /* margin-top: 5rem;
    padding-left: 3rem; */
    display: flex;
    flex-wrap: wrap;
`;

const WritePostButtonWrapper = styled(Responsive)`
    /* display: flex;
    justify-content: flex-end; */
    margin-top: 8rem;
    margin-bottom: 5rem;
    font-weight: bold;
    padding-bottom: 0.75rem;
    .post-count {
        color: ${palette.violet[4]};
        font-size: 1.125rem;
    }
    //border-bottom: 1px solid ${palette.gray[3]};
`;

const PostItemBlock = styled.div`
    width: 320px;
    height: 560px;
    padding: 1rem;
    overflow-y: hidden;
    .postItem-pic{
        background:linear-gradient( to right, #42275a, #734b6d );
        height: 200px;
        margin-bottom: 1rem;
        border-radius: 4px;
        img {
            height: 100%;
            width: 100%;
            border-radius: 4px;
        }
    }
    .postItem-category {
        margin-bottom: 1rem;
        font-weight: bold;
        color: ${palette.violet[3]};
        font-size: 0.875rem;
    }
    /* padding-top: 1rem;
    padding-bottom: 2rem; */
    /* &:first-child {
        margin-right: 0.25rem;
    }
    & + & {
        margin-right: 0.25rem;
    } */
    h3 {
        font-size: 1.5rem;
        margin-top: 0;
        margin-bottom: 0;
        &:hover {
            color: ${palette.gray[7]};
        }
    }
    p {
        color: ${palette.gray[6]};
    }
    
    .postItem-space {
        height: 1rem;
    }
`;

const PostItem = ({post}) => {
    const {title, body, publishedDate, tags, user, id, thumbnail} = post;
    return (
        <PostItemBlock>
            <Link to={`/@${user.username}/${id}`}>
                <div className="postItem-pic">
                    {thumbnail && <img src={`${thumbnail}`} />}
                    {/* <img src="https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile7.uf.tistory.com%2Fimage%2F24283C3858F778CA2EFABE" /> */}
                </div>
            </Link>
            <div className="postItem-category">카테고리</div>
            <h3>
                <Link to={`/@${user.username}/${id}`}>{title}</Link>
            </h3>
            <p>{body}</p>
            <SubInfo username={user.username} publishedDate={new Date(publishedDate)} postItemFont />
            <div className="postItem-space" />
            <Tags tags={tags} changeFontSize />
        </PostItemBlock>
    );
}

const PostList = ({loading, error, posts, showWriteButton, countPosts}) => {
    if(error) {
        return <PostListBlock>Error</PostListBlock>
    }

    return (
        <>
            <WritePostButtonWrapper>
                {/* {showWriteButton && (
                    <Button violet to="/write">새 글 작성하기</Button>
                )} */}
                <div>
                    <span className="post-list">전체 포스트 </span><span className="post-count">{countPosts}</span>
                </div>
            </WritePostButtonWrapper>
            <PostListBlock>
                {!loading && countPosts === 0 && 
                    <div>
                        등록된 포스트가 없습니다.
                    </div>
                }
                {!loading && countPosts !== 0 &&(
                    <>
                        {posts.map(post => (
                            <PostItem post={post} key={post.id} />
                        ))}
                    </>
                )}
            </PostListBlock>
        </>
    );
}

export default PostList;