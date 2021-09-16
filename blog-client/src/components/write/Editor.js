import React, { useEffect, useRef } from "react";
import Quill from "quill";
import 'quill/dist/quill.bubble.css';
import styled from "styled-components";
import Responsive from "../common/Responsive";
import palette from "../../lib/styles/palette";
// import Select from 'react-select';

const EditorBlock = styled(Responsive)`
    padding-top: 5rem;
    padding-bottom: 5rem;
    /* .editor-select {
        margin-bottom: 5rem;
    } */
`;

const TitleInput = styled.input`
    font-size: 3rem;
    outline: none;
    padding-bottom: 0.5rem;
    border: none;
    border-bottom: 1px solid ${palette.gray[4]};
    margin-bottom: 2rem;
    width: 100%;
    background: none;
`;

const QuillWrapper = styled.div`
    .ql-editor {
        padding: 0;
        min-height: 420px;
        font-size: 1.125rem;
        line-height: 1.5;
    }
    .ql-editor.ql-blank::before {
        left: 0px;
    }
`;

const Editor = ({title, body, onChangeField}) => {
    const quillElement = useRef(null);
    const quillInstance = useRef(null);

    useEffect(() => {
        quillInstance.current = new Quill(quillElement.current, {
            theme: 'bubble',
            placeholder: '내용을 작성하세요',
            modules: {
                //https://quilljs.com/docs/modules/toolbar/ 참고
                toolbar: [
                    [{header: '1'}, {header: '2'}],
                    ['bold', 'italic', 'underline', 'strike'],
                    [{list: 'ordered'}, {list: 'bullet'}],
                    ['blockquote', 'code-block', 'link', 'image'],
                ],
            }
        });

        //quill에 text-change 이벤트 핸들러 등록
        //참고: https://quilljs.com/docs/api/#events
        const quill = quillInstance.current;
        quill.on('text-change', (delta, oldDelta, source) => {
            if(source === 'user') {
                onChangeField({key: 'body', value: quill.root.innerHTML});
            }
        });
    }, [onChangeField]);

    const mounted = useRef(false); //mount되고나서 단 한 번만 실행하게
    useEffect(() => {
        if(mounted.current) return;
        mounted.current = true;
        quillInstance.current.root.innerHTML = body;
    }, [body]);

    const onChangeTitle = e => {
        onChangeField({key: 'title', value: e.target.value});
    };

    // const options = useMemo(() => [
    //     {value: "category1", label: "category1"},
    //     {value: "category2", label: "category2"},
    // ], []);

    return (
        <EditorBlock>
            {/* <div className="editor-select">
                <Select options={options} />
            </div> */}
            <TitleInput placeholder="제목을 입력하세요" onChange={onChangeTitle} value={title} />
            <QuillWrapper>
                <div ref={quillElement} />
            </QuillWrapper>
        </EditorBlock>
    );
}

export default Editor;