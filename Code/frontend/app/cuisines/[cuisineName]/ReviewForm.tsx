"use client";

import { useState } from "react";
import { createReview } from "@/app/lib/api";

interface Props {
  cuisineId: number;
}

export default function ReviewForm({ cuisineId }: Props) {
  const [rating, setRating] = useState(5);
  const [comment, setComment] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      await createReview({
        cuisineId,
        rating,
        comment,
      });

      window.location.reload();
    } catch (error) {
      console.error("Error creating review:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <h2 className="text-2xl font-semibold text-black mb-4">
        Write a Review
      </h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-black mb-1">Rating</label>
          <select
            value={rating}
            onChange={(e) => setRating(Number(e.target.value))}
            className="w-full border text-black rounded px-3 py-2"
          >
            {[5, 4, 3, 2, 1].map((num) => (
              <option key={num} value={num}>
                {num}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-black mb-1">Comment</label>
          <textarea
            value={comment}
            onChange={(e) => setComment(e.target.value)}
            maxLength={1000}
            className="w-full border text-black rounded px-3 py-2"
            rows={4}
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="bg-black text-white px-6 py-2 rounded hover:bg-gray-800 transition"
        >
          {loading ? "Submitting..." : "Submit Review"}
        </button>
      </form>
    </>
  );
}