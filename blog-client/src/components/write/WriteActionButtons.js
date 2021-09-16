import React from "react";
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import Button from "../common/Button";

const WriteActionButtonsBlock = styled.div`
    margin-top: 1rem;
    //margin-bottom: 3rem;
    padding-top: 2rem;
    padding-bottom: 3rem;
    button + button {
        margin-left: 0.5rem;
    }
    border-top: 1px solid ${palette.gray[3]};
`;

const StyledButton = styled(Button)`
    height: 2.125rem;
    & + & {
        margin-left: 0.5rem;
    }
`;

const WriteActionButtons = ({onCancel, onPublish, isEdit}) => {
    return (
        <WriteActionButtonsBlock>
            <StyledButton violet onClick={onPublish}>포스트 {isEdit ? '수정' : '등록'}</StyledButton>
            <StyledButton frame onClick={onCancel}>취소</StyledButton>
        </WriteActionButtonsBlock>
    );
}

export default WriteActionButtons;