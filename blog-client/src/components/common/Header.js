import React, { useState } from "react";
import styled from "styled-components";
import Responsive from "./Responsive";
import Button from "./Button";
import { Link } from "react-router-dom";
import palette from "../../lib/styles/palette";
import {RiMapPinUserFill} from 'react-icons/ri';
import SetUserInfo from "../settings/SetUserInfo";

const HeaderBlock = styled.div`
    position: fixed;
    width: 100%;
    background: rgba(255, 255, 255, 0.9);
    box-shadow: 0px 1px 3px ${palette.gray[2]};
    z-index: 5;
`;

const Wrapper = styled(Responsive)`
    height: 4rem;
    display: flex;
    align-items: center;
    justify-content: space-between; //서로간의 여백 최대
    color: ${palette.violet[3]};
    .logo {
        font-size: 2rem;
        font-weight: 800;
        letter-spacing: 2px;
    }
    .right {
        color: ${palette.gray[6]};
        .settings {
            display: flex;
            cursor: pointer;
            svg {
                font-size: 2rem;
                margin-right: 0.25rem;
            }
        }
    }
`;

const Spacer = styled.div`
    height: 4rem;
`;

const UserInfo = styled.div`
    font-size: 1.25rem;
    font-weight: bold;
    margin-right: 1.5rem;
`;

const AuthButton = styled(Button)`
    background: none;
    color: ${palette.violet[2]};
    border: 1px solid ${palette.violet[2]};
    border-radius: 16px;
    &:hover {
                background: none;
                border: 1px solid ${palette.violet[1]};
                color: ${palette.violet[1]};
            }
`;

const Header = ({user, onLogout}) => {
    const [settings, setSettings] = useState(false);

    const onSettingsClick = () => {
        setSettings(prev => !prev);
    }

    return (
        <>
            <HeaderBlock>
                <Wrapper>
                    <Link to="/" className="logo">KIRRIS</Link>
                    {user ? (
                        <div className="right">
                            <SetUserInfo username={user.username} visible={settings}>
                                <AuthButton onClick={onLogout}>Log out</AuthButton>
                            </SetUserInfo>
                            <div className="settings" onClick={onSettingsClick}>
                            <RiMapPinUserFill />
                            <UserInfo>
                                {user.username}
                            </UserInfo>
                            </div>
                        </div>
                    ) : (
                        <div className="right">
                            <AuthButton to="/login">Sign in</AuthButton>
                        </div>
                    )}
                </Wrapper>
            </HeaderBlock>
            <Spacer />
        </>
    );
}

export default Header;