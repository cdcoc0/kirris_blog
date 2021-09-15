import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import {IoIosSettings} from 'react-icons/io';
import {RiPencilFill} from 'react-icons/ri';

const SetUserInfoBlock = styled.div`
    position: absolute;
    top: 3.5rem;
    z-index: 30;
    width: 320px;
    min-height: 200px;
    background: white;
    border: 1px solid ${palette.gray[1]};
    box-shadow: 0px 1px 1px ${palette.gray[2]};
    display: flex;
    flex-direction: column;
    color: ${palette.gray[7]};
`;

const SetUserInfoHeader = styled.div`
    padding: 0.75rem 1rem;
    font-weight: bold;
`;

const SetUserInfoBody = styled.div`
    padding: 1rem;
    border-top: 1px solid ${palette.gray[2]};
    border-bottom: 1px solid ${palette.gray[2]};
    flex: 1;
    .body-title {
        color: ${palette.gray[5]};
        font-size: 0.825rem;
        font-weight: bold;
        margin-bottom: 0.75rem;
    }
    .blog-title {
        display: flex;
        justify-content: space-between;
        .icon {
            margin-top: 0.125rem;
            svg{
                font-size: 1.25rem;
                color: ${palette.violet[2]};
                margin-left: 0.25rem;
                &:hover {
                    color: ${palette.violet[1]};
                }
            }
        }
    }
`;

const SetUserInfoFooter = styled.div`
    padding: 0.5rem 1rem;
    display: flex;
    flex-direction: row-reverse;
`;

const SetUserInfo = ({children, visible, username}) => {
    if(!visible) return null;

    return (
        <SetUserInfoBlock>
            <SetUserInfoHeader>
                블로그 관리
            </SetUserInfoHeader>
            <SetUserInfoBody>
                <div className="body-title">
                    {`${username} 님의 블로그`}
                </div>
                <div className="blog-title">
                    <Link to="/" className="title">블로그 타이틀</Link>
                    <div>
                        <Link to="/write" className="icon"><RiPencilFill /></Link>
                        <Link to="/settings" className="icon"><IoIosSettings /></Link>
                    </div>
                </div>
            </SetUserInfoBody>
            <SetUserInfoFooter>{children}</SetUserInfoFooter>
        </SetUserInfoBlock>
    );
}

export default SetUserInfo;