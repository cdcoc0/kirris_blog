import React from "react";
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import Responsive from "../common/Responsive";
import SubInfo from "../common/SubInfo";
import Tags from "../common/Tags";

const PostViewerBlock = styled(Responsive)`
    margin-top: 5rem;
    padding-left: 0;
    .tag-title {
        color: ${palette.violet[3]};
        //margin-bottom: 1rem;
        font-size: 1.125rem;
        display: flex;
        align-items: center;
        svg {
            margin-left: 0.25rem;
            margin-top: 0.25rem;
        }
    }
    .bottom-space {
        height: 4rem;
    }
`;

const PostHead = styled.div`
    border-bottom: 1px solid ${palette.gray[3]};
    padding-bottom: 3rem;
    margin-bottom: 3rem;
    h1 {
        font-size: 3rem;
        line-height: 1.5; //폰트 사이즈의 1.5배
        margin: 0;
    }
`;

const PostContent = styled.div`
    font-size: 1.3125rem;
    color: ${palette.gray[8]};
    min-height: 400px;
`;

const PostViewer = ({post, error, loading, actionButtons}) => {
    if(error) {
        if(error.response && error.response.status === 404) {
            return <PostViewerBlock>존재하지 않는 포스트입니다.</PostViewerBlock>
        }
        return <PostViewerBlock>Error</PostViewerBlock>
    }

    if(loading || !post) {
        return null;
    }

    const {title, body, tags, user, publishedDate} = post;
    return (
        <PostViewerBlock>
            <PostHead>
                <h1>{title}</h1>
                <SubInfo username={user.username} publishedDate={publishedDate} hasMarginTop />
            </PostHead>
            {actionButtons}
            <PostContent
                dangerouslySetInnerHTML={{__html: body}}
            />
            
            <div className="bottom-space" />
            <Tags tags={tags} hasMarginBottom tagTitle />
        </PostViewerBlock>
    );
}

export default PostViewer;