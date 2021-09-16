import qs from "qs";
import React from "react";
import styled, {css} from "styled-components";
import Button from "../common/Button";
import palette from '../../lib/styles/palette';
import {MdFirstPage, MdLastPage} from 'react-icons/md';
import {RiArrowLeftSLine ,RiArrowRightSLine} from 'react-icons/ri';
import {Link} from 'react-router-dom';

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
    background: none;
    padding-top: 0.125rem;
    padding-left: 0.175rem;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    border: 1px solid ${palette.violet[2]};
    margin: 0 0.5rem;
    svg {
        color: ${palette.violet[3]};
        font-size: 1.5rem;
    }
    &:hover {
        background: none;
        border: 1px solid ${palette.violet[1]};
        svg {
            /* color: #130f40; */
            color: ${palette.violet[2]};
        }
    }

    &:disabled {
        border: ${palette.gray[5]};
        svg {
                color: ${palette.gray[5]};
            }
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