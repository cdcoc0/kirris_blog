import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import Button from "../common/Button";
import palette from "../../lib/styles/palette";
import {FaUserCircle, FaUserPlus} from 'react-icons/fa';

const AuthFormBlock = styled.div`
    .title {
        display: flex;
        align-items: center;
        justify-content: center;
        color: ${palette.violet[3]};
        margin-bottom: 2rem;
        svg {
            margin-right: 0.5rem;
            font-size: 1.5rem;
        }
        h2 {
            margin: 0;
        }
    }
`;

const StyledInput = styled.input`
    font-size: 1rem;
    border: none;
    //background: ${palette.gray[1]};
    background: none;
    //border-radius: 4px;
    border-bottom: 1px solid ${palette.violet[3]};
    //border: 1px solid ${palette.violet[0]};
    padding: 0.5rem;
    outline: none;
    width: 100%;
    &:focus {
        color: $oc-teal-7;
        border-bottom: 1px solid black;
    }
    & + & {
        margin-top: 0.75rem;
    }
    ::placeholder {
        color: ${palette.gray[6]}
    }
`;

const ButtonWithMarginTop = styled(Button)`
    margin-top: 1.5rem;
`;

const Footer = styled.div`
    margin-top: 2rem;
    text-align: center;
    span {
        margin-right: 0.75rem;
        color: ${palette.gray[6]};
        font-style: italic;
    }
    a {
        color: #04aaff;
        text-decoration: underline;
    }
`;

const ErrorMessage = styled.div`
    color: red;
    text-align: center;
    margin-top: 1.25rem;
    font-size: 0.875rem;
`;

const textMap = {
    login: 'Log in',
    register: 'Sign up'
};

const AuthForm = ({type, form, onChange, onSubmit, error}) => {
    const text = textMap[type];

    return (
        <AuthFormBlock>
            <div className="title">
                {type === 'login' ? <FaUserCircle /> : <FaUserPlus />}
                <h2>{text}</h2>
            </div>
            <form onSubmit={onSubmit}>
                <StyledInput autoComplete="username" name="username" placeholder="ID" onChange={onChange} value={form.username} />
                <StyledInput autoComplete="new-password" name="password" placeholder="Password" type="password" onChange={onChange} value={form.password} />
                {type === 'register' && (
                    <StyledInput autoComplete="new-password" name="passwordConfirm" placeholder="Confirm password" type="password" onChange={onChange} value={form.passwordConfirm} />
                )}
                {error && <ErrorMessage>{error}</ErrorMessage>}
                <ButtonWithMarginTop violet fullWidth>Enter</ButtonWithMarginTop>
            </form>
            <Footer>
                {type === 'login' ? (
                    <>
                        <span>Not registered?</span>
                        <Link to="/register">create account</Link>
                    </>
                ) : (
                    <>
                        <span>Already have an account?</span>
                        <Link to="/login">sign in</Link>
                    </>
                )}
            </Footer>
        </AuthFormBlock>
    );
}

export default AuthForm;