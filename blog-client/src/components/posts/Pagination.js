import qs from "qs";
import React from "react";
import styled from "styled-components";
import Button from "../common/Button";
import palette from '../../lib/styles/palette';
import {MdFirstPage, MdLastPage} from 'react-icons/md';
import {RiArrowLeftSLine ,RiArrowRightSLine} from 'react-icons/ri';

const PaginationBlock = styled.div`
    width: 420px;
    margin: 3rem auto;
    padding-bottom: 3rem;
    display: flex;
    justify-content: space-between;
    .side {
        display: flex;
    }
`;

const StyledButton = styled(Button)`
    background: ${palette.violet[3]};
    padding-top: 0.125rem;
    padding-left: 0.175rem;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    margin: 0 0.5rem;
    svg {
        font-size: 1.5rem;
    }
    &:hover {
        background: ${palette.violet[2]};
    }
    /* transition: 0.1s ease-in; */

    &:disabled {
        &:hover {
            background: ${palette.gray[3]};
        }
    }

    & + & {
        margin-left: 0.75rem;
    }
`;

const PageNumber = styled.div``;

const buildLink = ({username, tag, page}) => {
    const query = qs.stringify({tag, page});
    return username ? `/@${username}/?${query}` : `/?${query}`;
}

const Pagination = ({page, lastPage, username, tag}) => {
    return (
        <PaginationBlock>
            <div className="side">
                <StyledButton disabled={page === 1}
                    to={page === 1 ? undefined : buildLink({username, tag, page: 1})}><MdFirstPage /></StyledButton>
                <StyledButton disabled={page === 1}
                    to={
                        page === 1 ? undefined : buildLink({username, tag, page: page - 1})
                    }
                ><RiArrowLeftSLine /></StyledButton>
                </div>
            <PageNumber>{page}</PageNumber>
            <div className="side">
                <StyledButton disabled={page === lastPage}
                    to={
                        page === lastPage ? undefined : buildLink({username, tag, page: page + 1})
                    }
                ><RiArrowRightSLine /></StyledButton>
                <StyledButton disabled={page === lastPage}
                    to={page === lastPage ? undefined : buildLink({username, tag, page: lastPage})}><MdLastPage /></StyledButton>
            </div>
        </PaginationBlock>
    );
}

export default Pagination;