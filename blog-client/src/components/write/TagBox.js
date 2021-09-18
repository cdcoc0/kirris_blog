import React, { useCallback, useEffect, useState } from "react";
import styled from "styled-components";
import palette from "../../lib/styles/palette";

const TagBoxBlock = styled.div`
    width: 100%;
    border-top: 1px solid ${palette.gray[3]};
    padding-top: 2rem;
    h4 {
        color: ${palette.gray[7]};
        margin-top: 0;
        margin-bottom: 0.5rem;
    }
`;

const TagForm = styled.form`
    border-radius: 4px;
    overflow: hidden;
    display: flex;
    width: 256px;
    border: 1px solid ${palette.gray[6]};
    
    input,
    button {
        outline: none;
        border: none;
        font-size: 1rem;
    }
    input {
        padding: 0.5rem;
        flex: 1;
        min-width: 0;
    }
    button {
        cursor: pointer;
        padding-right: 1rem;
        padding-left: 1rem;
        border: none;
        background: ${palette.violet[3]};
        color: white;
        font-weight: bold;
        &:hover {
            background: ${palette.violet[4]};
        }
    }
`;

const Tag = styled.div`
    margin-right: 0.5rem;
    color: ${palette.gray[6]};
    &:hover {
        opacity: 0.5;
    }
`;

const TagListBlock = styled.div`
    display: flex;
    margin-top: 0.5rem;
`;

//React.memo를 사용해 tag값이 바뀔 때만 리렌더링되도록 처리
const TagItem = React.memo(({tag, onRemove}) => <Tag onClick={() => onRemove(tag)}>#{tag}</Tag>);

//React.memo를 사용해 tags 값이 바뀔 때만 리렌더링되도록 처리
const TagList = React.memo(({tags, onRemove}) => (
    <TagListBlock>
        {tags.map(t => (
            <TagItem key={t} tag={t} onRemove={onRemove} />
        ))}
    </TagListBlock>
));

const TagBox = ({tags, onChangeTags}) => {
    //tag는 이 컴포넌트에서 관리
    //리덕스로 tags 배열 관리
    
    const [input, setInput] = useState('');
    const [localTags, setLocalTags] = useState([]);

    const insertTag = useCallback(
        tag => {
            if(!tag) return;
            if(localTags.includes(tag)) return;
            const nextTags = [...localTags, tag];
            setLocalTags(nextTags);
            onChangeTags(nextTags);
        },
        [localTags, onChangeTags]
    );

    const onRemove = useCallback(
        tag => {
            const nextTags = localTags.filter(t => t !== tag);
            setLocalTags(nextTags);
            onChangeTags(nextTags);
        },
        [localTags, onChangeTags]
    );

    const onChange = useCallback(
        e => {
            setInput(e.target.value);
        },
        []
    );

    const onSubmit = useCallback(
        e => {
            e.preventDefault();
            insertTag(input.trim());
            setInput('');
        },
        [input, insertTag]
    )

    useEffect(() => {
        setLocalTags(tags);
    }, [tags]);

    return (
        <TagBoxBlock>
            <h4>태그</h4>
            <TagForm onSubmit={onSubmit}>
                <input value={input} onChange={onChange} />
                <button>추가</button>
            </TagForm>
            <TagList tags={localTags} onRemove={onRemove} />
        </TagBoxBlock>
    );
}

export default TagBox;