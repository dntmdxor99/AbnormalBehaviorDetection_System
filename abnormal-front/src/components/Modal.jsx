import React from 'react';
import styled from 'styled-components';

const ModalWrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const Modal = ({ isOpen, message, onClose }) => {
  if (!isOpen) return null;

  return (
    <ModalWrapper>
      <p>{message}</p>
      <button onClick={onClose}>닫기</button>
    </ModalWrapper>
  );
};

export default Modal;