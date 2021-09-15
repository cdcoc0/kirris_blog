import React from "react";
import { Link } from "react-router-dom";
import styled, {css} from "styled-components";
import palette from "../../lib/styles/palette";

const SubInfoBlock = styled.div`
    color: ${palette.gray[6]};
    ${props =>
        props.hasMarginTop &&
        css`
        margin-top: 1rem;
    `}
    
    /* span 사이에 가운뎃점 */
    span + span:before {
        color: ${palette.gray[4]};
        padding-left: 0.25rem;
        padding-right: 0.25rem;
        content: '\\B7';
    }
    ${props => 
        props.postItemFont &&
        css`
        font-size: 0.825rem;
        font-weight: 600;
        color: ${palette.gray[5]};
        span + span:before {
            padding-left: 0.5rem;
            padding-right: 0.5rem;
        }
        `}
`;

const SubInfo = ({username, publishedDate, hasMarginTop, postItemFont}) => {
    return (
        <SubInfoBlock hasMarginTop={hasMarginTop} postItemFont={postItemFont}>
            <span>
                <b>
                    <Link to={`/@${username}`}>{username}</Link>
                </b>
            </span>
            <span>{new Date(publishedDate).toLocaleDateString()}</span>
            <span>3 COMMENTS</span>
        </SubInfoBlock>
    );
}

export default SubInfo;