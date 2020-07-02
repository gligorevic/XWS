import React, { useState } from "react";
import Carousel, { Modal, ModalGateway } from "react-images";
import "./Gallery.css";

export default function Gallery({ images }) {
  const [currentIndex, setCurrentIndex] = useState(-1);

  const closeModal = () => {
    setCurrentIndex(-1);
  };

  return (
    <div style={{ overflow: "hidden", zIndex: 100 }}>
      {images.map((image, i) => (
        <div className="imageContainer">
          <img src={image.source} onClick={() => setCurrentIndex(i)} />
        </div>
      ))}
      <ModalGateway>
        {currentIndex != -1 ? (
          <Modal onClose={closeModal}>
            <Carousel currentIndex={currentIndex} views={images} />
          </Modal>
        ) : null}
      </ModalGateway>
    </div>
  );
}
