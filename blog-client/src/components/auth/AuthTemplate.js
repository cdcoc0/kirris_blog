import React from "react";
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import { Link } from "react-router-dom";

const AuthTemplateBlock = styled.div`
    /* 화면 전채를 채움 */
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    /* 내부 중앙 정렬 */
    display: flex;
    justify-content: center;
    align-items: center;
`;

const WhiteBox = styled.div`
    .logo-area {
        display: block;
        padding-bottom: 3rem;
        text-align: center;
        font-weight: bold;
        letter-spacing: 2px;
        color: ${palette.violet[3]};
    }
    margin-top: 2rem;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.025);
    padding: 2rem;
    width: 360px;
    border-radius: 4px;
    border: 1px solid ${palette.violet[3]};
`

const HomeButton = styled(Link)`
    position: absolute;
    top: 3rem;
    left: 3rem;
    z-index: 20;
    background: none;
    outline: none;
    border: none;
    color: ${palette.violet[3]};
    font-size: 2rem;
`;

const AuthTemplate = ({children}) => {
    return (
        <>
            <HomeButton to="/">
            </HomeButton>
            <AuthTemplateBlock>
                <WhiteBox>
                    <div className="logo-area">
                        <Link to="/">KIRRIS</Link>
                    </div>
                    {children}
                </WhiteBox>
            </AuthTemplateBlock>
        </>
    );
}

export default AuthTemplate;