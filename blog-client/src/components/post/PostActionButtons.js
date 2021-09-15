import React, { useState } from "react";
import styled from "styled-components";
import palette from "../../lib/styles/palette";
import AskRemoveModal from "./AskRemoveModal";

const PostActionButtonsBlock = styled.div`
    display: flex;
    justify-content: flex-end;
    margin-bottom: 2rem;
    margin-top: -1.5rem;
`;

const ActionButton = styled.button`
    padding: 0.25rem 0.5rem;
    border-radius: 4px;
    color: ${palette.violet[3]};
    font-weight: bold;
    border: none;
    outline: none;
    font-size: 0.875rem;
    cursor: pointer;
    &:hover {
        background: ${palette.gray[1]};
        color: ${palette.violet[5]};
    }
    & + & {
        margin-left: 0.25rem;
    }
`;

const PostActionButtons = ({onEdit, onRemove}) => {
    const [modal, setModal] = useState(false);

    const onRemoveClick = () => {
        setModal(true);
    }

    const onConfirm = () => {
        setModal(false);
        onRemove();
    }

    const onCancel = () => {
        setModal(false);
    }

    return (
        <>
            <PostActionButtonsBlock>
                <ActionButton onClick={onEdit}>수정</ActionButton>
                <ActionButton onClick={onRemoveClick}>삭제</ActionButton>
            </PostActionButtonsBlock>
            <AskRemoveModal
                visible={modal}
                onConfirm={onConfirm}
                onCancel={onCancel}
            />
        </>
    );
}

export default PostActionButtons;